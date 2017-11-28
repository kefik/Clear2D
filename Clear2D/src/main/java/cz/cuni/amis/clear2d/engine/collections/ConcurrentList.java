package cz.cuni.amis.clear2d.engine.collections;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import cz.cuni.amis.clear2d.tests.Test00_ConcurrentList;

/**
 * Shielded against {@link ConcurrentModificationException}, all {@link #iterator()} are safe to use concurrently at the same time
 * even if you use {@link #add(Object)} (and alikes) or {@link #remove(int)} (and alikes) from different threads.
 * 
 * This is NOT A {@link CopyOnWriteArrayList} !!! It's more like synchronized {@link ArrayList} but immune to {@link ConcurrentModificationException} as well!
 * Multi-threaded test: {@link Test00_ConcurrentList}. Cca 2.5x slower than non-synced {@link ArrayList}.
 * 
 * You cannot use {@link Iterator#remove()}, not supported.
 * 
 * {@link ListIterator} not supported at all, just basic {@link Iterator}!
 * 
 * Note that inherently, methods like {@link #size()}, {@link #isEmpty()}, {@link #indexOf(Object)}, {@link #lastIndexOf(Object)} does not have any meaning in the concurrent environment...
 * Note that we purposefully use synchronized(this) to control the concurrency! If you want to use unsafe method like those previously stated, synchronize on the list and do your stuff. 
 * 
 * NEVER THROWS {@link IndexOutOfBoundsException} DUE TO THE CONCURRENT NATURE ... you might just have invalidated index...
 * 
 * @author Jimmy
 *
 * @param <E>
 */
public class ConcurrentList<E> implements List<E> {
	
	protected static class Ref<DATA> {
		
		public final DATA data;
		
		public Ref(DATA data) {
			this.data = data;
		}
		
		@Override
		public String toString() {
			return "Ref[" + data + "]";
		}
		
	}
	
	protected List<Ref<E>> values = new ArrayList<Ref<E>>();
	
	protected int delta = 0;
	
	protected int consolidatedUpToIndex = -1;
	
	protected List<WeakReference<ConcurrentIterator>> iterators = new ArrayList<WeakReference<ConcurrentIterator>>();
	
	public class ConcurrentIterator implements Iterator<E> {

		protected int index = -1;
		
		protected boolean advanced = false;
		
		protected E lastElement = null;

		/**
		 * Unsynced!
		 */
		private void advanceUnsync() {
			if (!advanced) ++index;
			advanced = true;
			if (index >= values.size()) return;			
			while (index < values.size()) {
				if (values.get(index) == null) {
					trueRemoveUnsync(index);
				} else {
					break;
				}
			}			
		}
		
		@Override
		public boolean hasNext() {
			synchronized(ConcurrentList.this) {
				advanceUnsync();
				return index < values.size();
			}
		}

		@Override
		public E next() {			
			synchronized(ConcurrentList.this) {
				advanceUnsync();
				if (index < values.size()) {
					advanced = false;					
					return lastElement = values.get(index).data;
				}
			}
			return null;
		}
		
		@Override
		public void remove() {
			synchronized(this) {
				if (index-1 > 0 && index-1 < values.size())
					if (values.get(index) != null && values.get(index).data == lastElement) {
						values.set(index-1, null);
						++delta;
						if (index <= consolidatedUpToIndex) consolidatedUpToIndex = index-1;
					}
			}
		}
		
		public void dispose() {
			synchronized(ConcurrentList.this) {
				for (int i = 0; i < ConcurrentList.this.iterators.size(); ++i) {
					WeakReference<ConcurrentIterator> iteratorRef = ConcurrentList.this.iterators.get(i);
					if (iteratorRef.get() == this) {
						ConcurrentList.this.iterators.remove(i);
						return;
					}
				}				
			}
		}
		
	}
	
	private class ConcurrentListIterator extends ConcurrentIterator implements ListIterator<E> {

		@Override
		public boolean hasPrevious() {
			throw new RuntimeException("Unsupported operation!");
		}

		@Override
		public E previous() {
			throw new RuntimeException("Unsupported operation!");
		}

		@Override
		public int nextIndex() {
			throw new RuntimeException("Unsupported operation!");
		}

		@Override
		public int previousIndex() {
			throw new RuntimeException("Unsupported operation!");
		}

		@Override
		public void set(E e) {
			throw new RuntimeException("Unsupported operation!");
		}

		@Override
		public void add(E e) {
			throw new RuntimeException("Unsupported operation!");
		}

		@Override
		public void remove() {
			throw new RuntimeException("Unsupported operation!");
		}
		
	}
	
	/**
	 * Ensure that there are no holes in {@link #values} between indices 0-upToIndex (boundaries included).
	 * @param upToIndex
	 * @return returns whether 'upToIndex' is still valid index
	 */
	private boolean consolidateUnsync(int upToIndex) {
		if (upToIndex < 0) return false;
		if (delta == 0) return upToIndex < values.size();
		if (upToIndex <= consolidatedUpToIndex) return upToIndex < values.size();
		ConcurrentIterator iterator = iterator();			
		for (int i = 0; i <= upToIndex && delta > 0; ++i) {
			iterator.next();
		}	
		iterator.dispose();
		consolidatedUpToIndex = upToIndex;
		if (consolidatedUpToIndex >= values.size()) {
			consolidatedUpToIndex = values.size()-1;
			return false;
		}
		return true;
	}
	
	/**
	 * Element is going to be removed from 'index' position within {@link #values}.
	 * @param index
	 */
	private void toBeRemovedUnsync(int index) {
		Iterator<WeakReference<ConcurrentIterator>> weakIterators = this.iterators.iterator();
		while (weakIterators.hasNext()) {
			WeakReference<ConcurrentIterator> weakIterator = weakIterators.next();
			ConcurrentIterator iterator = weakIterator.get();
			
			// ITERATOR NO-LONGER VALID
			if (iterator == null) {
				weakIterators.remove();
				continue;
			}
			
			if (iterator.index > index) {
				iterator.index--;
			} else
			if (iterator.index == index) {
				iterator.advanced = true;
			}			
		}		
	}
	
	/**
	 * Element is going to be added onto 'index' position within {@link #values}.
	 * @param index
	 */
	private void toBeAddedUnsync(int index) {
		if (index >= values.size()) return;
		
		Iterator<WeakReference<ConcurrentIterator>> weakIterators = this.iterators.iterator();
		while (weakIterators.hasNext()) {
			WeakReference<ConcurrentIterator> weakIterator = weakIterators.next();
			ConcurrentIterator iterator = weakIterator.get();
			
			// ITERATOR NO-LONGER VALID
			if (iterator == null) {
				weakIterators.remove();
				continue;
			}
			
			if (iterator.index > index) {
				iterator.index++;
			} else
			if (iterator.index == index) {
				iterator.advanced = true;
			}			
		}		
	}
	
	private void trueRemoveUnsync(int index) {
		toBeRemovedUnsync(index);
		Ref<E> element = values.remove(index);
		if (element == null) --delta;		
	}

	@Override
	public int size() {
		int result = values.size() - delta;
		if (result < 0) return 0;
		return result;
	}

	@Override
	public boolean isEmpty() {
		return values.size() - delta <= 0;
	}

	@Override
	public boolean contains(Object o) {
		synchronized(this) {
			return values.contains(o);
		}
	}

	@Override
	public ConcurrentIterator iterator() {
		synchronized(this) {
			ConcurrentIterator iterator = new ConcurrentIterator();
			iterators.add(new WeakReference<ConcurrentIterator>(iterator));			
			return iterator;
		}
	}

	@Override
	public Object[] toArray() {		
		synchronized(this) {
			Object[] array = new Object[size()];
			int i = 0;
			for (Ref<E> reference : values) {
				if (reference == null) continue;
				array[i++] = reference.data;
			}
			return array;
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized(this) {
			if (a == null || a.length != size()) {
				return (T[]) toArray();
			}
			int i = 0;
			for (Ref<E> ref : values) {
				if (ref == null) continue;
				a[i++] = (T) ref.data;
			}
			return a;
		}
	}

	@Override
	public boolean add(E e) {
		synchronized(this) {
			values.add(new Ref<E>(e));
			if (consolidatedUpToIndex+2 >= values.size()) ++consolidatedUpToIndex;
		}
		return true;
	}

	@Override
	public boolean remove(Object o) {
		synchronized(this) {
			ConcurrentIterator iterator = iterator();
			int index = -1;
			while (iterator.hasNext()) {
				++index;
				E element = iterator.next();
				if (element == null) {
					if (o == null) {
						remove(index);
						iterator.dispose();
						return true;
					}
				} else
				if (element.equals(o)) {
					remove(index);
					iterator.dispose();
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized(this) {
			for (Object o : c) {
				if (!contains(o)) return false;
			}
			return true;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		synchronized(this) {
			for (Object o : c) {
				add((E)o);
			}
			return true;
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		synchronized(this) {
			int i = -1;
			for (Object o : c) {
				++i;
				add(index + i, (E)o);
			}
			return true;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {		
		synchronized(this) {
			boolean result = true;
			for (Object o : c) {
				result = remove(o) && result;
			}
			return result;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized(this) {
			ConcurrentIterator iterator = iterator();
			boolean changed = false;
			while (iterator.hasNext()) {
				E e = iterator.next();
				if (!c.contains(e)) {
					remove(iterator.index);
					changed = true;
				}
			}
			iterator.dispose();
			return changed;
		}
	}

	@Override
	public void clear() {
		synchronized(this) {
			values.clear();		
			consolidatedUpToIndex = -1;
			delta = 0;
		}
	}

	@Override
	public E get(int index) {
		synchronized(this) {
			if (consolidateUnsync(index)) {
				return values.get(index).data;
			}
			return null;
		}		
	}

	@Override
	public E set(int index, E element) {
		synchronized(this) {
			if (consolidateUnsync(index)) {
				return values.set(index, new Ref<E>(element)).data;
			}
			return null;
		}
	}

	/**
	 * @param index if 
	 */
	@Override
	public void add(int index, E element) {
		synchronized(this) {
			if (!consolidateUnsync(index)) {
				if (index <= 0) {
					if (values.size() == 0) add(element);
					else index = 0;
				}
				else {
					add(element);
					return;
				}
			}
			values.add(index, new Ref<E>(element));	
			if (index < consolidatedUpToIndex) ++consolidatedUpToIndex;
		}
		
	}

	@Override
	public E remove(int index) {
		synchronized(this) {
			if (consolidateUnsync(index)) {
				E removed = values.set(index, null).data;
				++delta;
				if (index <= consolidatedUpToIndex) consolidatedUpToIndex = index-1;
				return removed;
			} else {
				return null;
			}
		}		
	}

	@Override
	public int indexOf(Object o) {
		ConcurrentIterator iterator = iterator();
		int index = -1;
		while (iterator.hasNext()) {
			++index;
			if (o == null) {
				if (iterator.next() == null) {
					iterator.dispose();
					return index; 
				}
			} else {
				if (o.equals(iterator.next())) {
					iterator.dispose();
					return index;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		ConcurrentIterator iterator = iterator();
		int index = -1;
		int lastIndex = 0;
		while (iterator.hasNext()) {
			++index;
			if (o == null) {
				if (iterator.next() == null) { 
					lastIndex = index;
				} 
			} else {
				if (o.equals(iterator.next())) {
					lastIndex = index;
				}
			}
		}
		iterator.dispose();
		return lastIndex;
	}

	@Override
	public ListIterator<E> listIterator() {
		synchronized(this) {
			ConcurrentListIterator iterator = new ConcurrentListIterator();
			iterators.add(new WeakReference<ConcurrentIterator>(iterator));			
			return iterator;
		}
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		synchronized(this) {
			ConcurrentListIterator iterator = new ConcurrentListIterator();
			iterators.add(new WeakReference<ConcurrentIterator>(iterator));
			for (int i = 0; i < index; ++i) {
				if (!iterator.hasNext()) break;
				iterator.next();
			}
			return iterator;
		}
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		ConcurrentList<E> result = new ConcurrentList<E>();
		synchronized(this) {
			ConcurrentListIterator iterator = new ConcurrentListIterator();
			for (int i = 0; i < fromIndex; ++i) {
				if (!iterator.hasNext()) break;
				iterator.next();
			}
			for (int i = fromIndex; i < toIndex; ++i) {
				if (iterator.hasNext()) {
					result.add(iterator.next());
				} else {
					break;
				}
			}
			iterator.dispose();
		}		
		return result;
	}

}

package cz.cuni.amis.clear2d.engine.collections;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

/**
 * Update order of elements is guaranteed.
 * 
 * @author Jimmy
 *
 * @param <ELEM>
 */
public class C2DUpdatables<ELEM extends IUpdatable> implements IUpdatable {

	private ConcurrentList<ELEM> toAdd = new ConcurrentList<ELEM>();
	
	private ConcurrentList<ELEM> toRemove = new ConcurrentList<ELEM>();
	
	private ConcurrentList<ELEM> updatables = new ConcurrentList<ELEM>();
	
	public void add(ELEM updatable) {
		if (!toRemove.remove(updatable)) {
			toAdd.add(updatable);
		}
	}
	
	public boolean contains(ELEM updatable) {
		return updatables.contains(updatable) || toAdd.contains(updatable);
	}
	
	public void remove(ELEM updatable) {
		if (!toAdd.remove(updatable)) {
			toRemove.add(updatable);
		}
	}
	
	@Override
	public void update() {
		if (toRemove.size() > 0) {
			updatables.removeAll(toRemove);
			toRemove.clear();
		}
		if (toAdd.size() > 0) {
			updatables.addAll(toAdd);
			toAdd.clear();
		}
		
		@SuppressWarnings("unchecked")
		ConcurrentList<IUpdatable>.ConcurrentIterator iterator = (ConcurrentList<IUpdatable>.ConcurrentIterator) updatables.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}
		iterator.dispose();
	}
	
	@Override
	public String toString() {
		return "C2DUpdatables[#count = " + updatables.size() + "]";
	}

}

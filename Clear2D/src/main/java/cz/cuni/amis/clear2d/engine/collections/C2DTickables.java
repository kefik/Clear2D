package cz.cuni.amis.clear2d.engine.collections;

import java.util.HashSet;
import java.util.Set;

import cz.cuni.amis.clear2d.engine.iface.IRenderable;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

/**
 * Tick-order of elements is not guaranteed.
 * 
 * @author Jimmy
 *
 * @param <ELEM>
 */
public class C2DTickables<ELEM extends ITickable> implements IUpdatable {
	
	private ConcurrentList<ELEM> toAdd = new ConcurrentList<ELEM>();
	
	private ConcurrentList<ELEM> toRemove = new ConcurrentList<ELEM>();
	
	private ConcurrentList<ELEM> tickables = new ConcurrentList<ELEM>();

	private C2DTime time;
	
	public C2DTickables(C2DTime time) {
		this.time = time;
	}
	
	public void add(ELEM tickable) {
		if (!toRemove.remove(tickable)) {
			toAdd.add(tickable);
		}
	}
	
	public boolean contains(ELEM tickable) {
		return tickables.contains(tickable) || toAdd.contains(tickable);
	}
	
	public void remove(ELEM tickable) {
		if (!toAdd.remove(tickable)) {
			toRemove.add(tickable);
		}
	}
	
	@Override
	public void update() {
		if (toRemove.size() > 0) {
			tickables.removeAll(toRemove);
			toRemove.clear();
		}
		if (toAdd.size() > 0) {
			tickables.addAll(toAdd);
			toAdd.clear();
		}
		
		@SuppressWarnings("unchecked")
		ConcurrentList<ITickable>.ConcurrentIterator iterator = (ConcurrentList<ITickable>.ConcurrentIterator) tickables.iterator();
		while (iterator.hasNext()) {
			iterator.next().tick(time);
		}
		iterator.dispose();
	}
	
	@Override
	public String toString() {
		return "C2DTickables[#count=" + tickables.size() + "]";
	}

}

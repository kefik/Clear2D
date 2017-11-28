package cz.cuni.amis.clear2d.engine.collections;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.iface.IRenderable;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

/**
 * Rendering order of elements is guaranteed.
 * @author Jimmy
 *
 * @param <ELEM>
 */
public class C2DRenderables<ELEM extends IRenderable> implements IUpdatable {

	private ConcurrentList<ELEM> toAdd = new ConcurrentList<ELEM>();
	
	private ConcurrentList<ELEM> toRemove = new ConcurrentList<ELEM>();
	
	private ConcurrentList<ELEM> renderables = new ConcurrentList<ELEM>();

	private Camera camera;
	
	public C2DRenderables(Camera camera) {
		this.camera = camera;
	}
	
	public void add(ELEM renderable) {
		if (!toRemove.remove(renderable)) {
			toAdd.add(renderable);
		}		
	}
	
	public boolean contains(ELEM renderable) {
		return renderables.contains(renderable) || toAdd.contains(renderable);
	}
	
	public void remove(ELEM renderable) {
		if (!toAdd.remove(renderable)) {
			toRemove.add(renderable);
		}
	}
	
	@Override
	public void update() {
		if (toRemove.size() > 0) {
			renderables.removeAll(toRemove);
			toRemove.clear();
		}
		if (toAdd.size() > 0) {
			renderables.addAll(toAdd);
			toAdd.clear();
		}
		
		@SuppressWarnings("unchecked")
		ConcurrentList<IRenderable>.ConcurrentIterator iterator = (ConcurrentList<IRenderable>.ConcurrentIterator) renderables.iterator();
		while (iterator.hasNext()) {
			iterator.next().render(camera);
		}
		iterator.dispose();
	}
	
	@Override
	public String toString() {
		return "C2DRenderables[#count = " + renderables.size() + "]";
	}

}

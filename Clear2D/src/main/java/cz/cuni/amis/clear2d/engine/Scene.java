package cz.cuni.amis.clear2d.engine;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.iface.IControllable;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

/**
 * {@link #disable()}d by default!
 * 
 * Do not forget to {@link #enable()} after you bind the camera!
 * @author Jimmy
 */
public class Scene implements IUpdatable, ITickable, IControllable {
	
	/**
	 * Not to be manipulated outside this class...
	 */
	public final SceneRoot root;
	
	/**
	 * Scene gets rendered into this camera if not null.
	 * 
	 * May be changed.
	 */
	public Camera camera;
	
	public boolean enabled = false;
	
	public Scene() {
		this(null);
	}
	
	public Scene(Camera camera) {		
		root = new SceneRoot();
		root.setEnabled(false);
		this.camera = camera;
	}
	
	@Override
	public void setEnabled(boolean state) {
		if (enabled == state) return;
		enabled = state;
		if (enabled) {
			root.setEnabled(true);
			Clear2D.engine.renderUpdate.add(this);
			Clear2D.engine.tickUpdate.add(this);
		} else {
			root.setEnabled(false);
			Clear2D.engine.tickUpdate.remove(this);
		}
	}

	/**
	 * Clear2D.engine.renderUpdate
	 */
	@Override
	public void update() {
		if (root == null) return;
		if (camera == null) return;
		
		camera.target.lock();
		try {
			root.notify(Events.RENDER, camera);
		} finally {
			camera.target.unlock();
		}
	}

	@Override
	public void tick(C2DTime time) {
		if (root == null) return;
		
		root.notify(Events.TICK, time);			
	}

}

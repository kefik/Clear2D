package cz.cuni.amis.clear2d.engine.components;

import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

public final class CTickCallback<CALLBACK extends ITickable> extends CTick {

	public CALLBACK callback;
	
	public CTickCallback() {		
	}
	
	/**
	 * @param callback {@link ITickable#tick(C2DTime)} callback
	 */
	public CTickCallback(CALLBACK callback) {
		this(null, callback);
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 * @param callback {@link ITickable#tick(C2DTime)} callback
	 */
	public CTickCallback(SceneElement owner, CALLBACK callback) {
		super(owner);
		this.callback = callback;
	}
	
	@Override
	public void tick(C2DTime time) {
		if (callback != null) {
			callback.tick(time);
		}
	}
	
}

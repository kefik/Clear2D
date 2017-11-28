package cz.cuni.amis.clear2d.engine.time;

import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

/**
 * Everything is in SECONDS, if not stated otherwise.
 * @author Jimmy
 */
public class TimeDelta implements IUpdatable {

	private long startMillis = -1;
	
	/**
	 * In SECONDS.
	 */
	public float last = -1;
	
	/**
	 * In SECONDS.
	 */
	public float curr = -1;
	
	/**
	 * In SECONDS.
	 */
	public float delta = -1;
	
	public TimeDelta() {
	}
	
	public void start() {
		startMillis = System.currentTimeMillis();
		last = 0;
		curr = Float.MIN_VALUE;
		delta = Float.MIN_VALUE;
	}
	
	public void update() {
		if (startMillis < 0) {
			start();
			return;
		}
		last = curr;
		curr = (float)(System.currentTimeMillis() - startMillis) / 1000.0f;
		delta = curr - last;
	}
	
}

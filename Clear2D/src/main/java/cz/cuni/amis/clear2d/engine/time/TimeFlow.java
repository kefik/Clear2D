package cz.cuni.amis.clear2d.engine.time;

import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

public class TimeFlow implements IUpdatable {

	private TimeDelta source;
	
	public float speed = 1.0f;
	
	/**
	 * In seconds!
	 */
	public float delta = -1;

	public TimeFlow(TimeDelta source) {
		this.source = source;
	}

	/**
	 * Must be updated AFTER its source!
	 */
	@Override
	public void update() {
		delta = source.delta * speed;
	}
	
}

package cz.cuni.amis.clear2d.engine.iface;

import cz.cuni.amis.clear2d.engine.events.Event;

public interface INotifiable {
	
	/**
	 * Notify the object about particular event.
	 * @param event
	 * @param params
	 */
	public void notify(Event event, Object... params);
	
}

package cz.cuni.amis.clear2d.engine;

import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;

/**
 * {@link Scene} root element. 
 * Clears the camera render target before it renders its children.
 */
public class SceneRoot extends SceneElement {
	
	public SceneRoot() {
	}
	
	@Override
	public void handleEvent(Event event, Object... params) {
		if (event == Events.RENDER) {
			((Camera)params[0]).target.clear();
		}
	}
	
}

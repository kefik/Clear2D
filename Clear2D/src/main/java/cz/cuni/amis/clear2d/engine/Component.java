package cz.cuni.amis.clear2d.engine;

import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.iface.IControllable;
import cz.cuni.amis.clear2d.engine.iface.INotifiable;

public class Component implements INotifiable, IControllable {

	/**
	 * READ-ONLY
	 */
	public SceneElement owner;
	
	/**
	 * Is component enabled?
	 * 
	 * Do not alter directly, use {@link #enabled} and {@link #disable()} instead.
	 */
	public boolean enabled = true;
	
	/**
	 * Construct the component as 'enabled' without owner {@link SceneElement}.
	 */
	public Component() {
		this.owner = null;
	}
	
	/**
	 * Construct the component as 'enabled' and add into 'owner' via {@link SceneElement#addComponent(Component)}.
	 * @param owner initial owner the component; if null, does nothing
	 */
	public Component(SceneElement owner) {
		if (owner != null) {
			owner.addComponent(this);
		}
	}

	@Override
	public void notify(Event event, Object... params) {
		if (!enabled) return;
		notifyDo(event, params);
	}
	
	protected void notifyDo(Event event, Object... params) {		
	}

	@Override
	public void setEnabled(boolean state) {
		enabled = state;		
	}
	
}

package cz.cuni.amis.clear2d.engine.components;

import cz.cuni.amis.clear2d.engine.Component;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

public class CTick extends Component implements ITickable {

	public CTick() {		
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 */
	public CTick(SceneElement owner) {
		super(owner);
	}
	
	protected void notifyDo(Event event, Object... params) {
		if (event == Events.TICK) tick((C2DTime)params[0]);
	}
	
	@Override
	public void tick(C2DTime time) {
	}
	
}

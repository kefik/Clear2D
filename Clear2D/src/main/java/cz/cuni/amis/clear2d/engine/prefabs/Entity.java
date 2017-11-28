package cz.cuni.amis.clear2d.engine.prefabs;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

public class Entity<TEXTURE extends IDrawable> extends Sprite<TEXTURE> implements ITickable {

	public Entity() {
		this(null);
	}
	
	public Entity(TEXTURE texture) {
		super(texture);
		Clear2D.engine.tickUpdate.add(this);
	}

	@Override
	public void handleEvent(Event event, Object... params) {
		if (event == Events.TICK) tick(((C2DTime)params[0]));
	}
	
	@Override
	public void tick(C2DTime time) {
	}
	
	@Override
	public String toString() {
		return "Entity";
	}

}

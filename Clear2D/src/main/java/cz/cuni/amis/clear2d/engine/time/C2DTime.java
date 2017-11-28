package cz.cuni.amis.clear2d.engine.time;

import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

public class C2DTime implements IUpdatable {
	
	public final TimeDelta real;
	
	public final TimeFlow game;
	
	public C2DTime() {
		real = new TimeDelta();
		game = new TimeFlow(real);	
	}

	@Override
	public void update() {
		real.update();
		game.update();
	}

}

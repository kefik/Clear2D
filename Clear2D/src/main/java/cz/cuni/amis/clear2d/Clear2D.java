package cz.cuni.amis.clear2d;

import cz.cuni.amis.clear2d.engine.collections.C2DTickables;
import cz.cuni.amis.clear2d.engine.collections.C2DUpdatables;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;
import cz.cuni.amis.clear2d.engine.time.C2DTimer;

public class Clear2D {

	/**
	 * Do not forget to {@link #start(Clear2DConfig)} ;-)
	 */
	public static final Clear2D engine = new Clear2D();
	
	public final C2DTimer timer;
		
	public final C2DTime time;
	
	public final C2DTickables<ITickable> tickUpdate;
	
	public final C2DUpdatables<IUpdatable> renderUpdate;
	
	public final C2DUpdatables<IUpdatable> presentUpdate;
	
	private Clear2D() {
		timer = new C2DTimer(60, false);
		
		// DEFINITION OF THE ORDER OF CALLBACK EXECUTIONS WITHIN {@link C2DTime}
		
		// FIRST TICK THE TIME
		time = new C2DTime();				
		timer.addUpdatable(time);
		
		// THEN TICK TICKING STUFF (simulation)
		tickUpdate = new C2DTickables<ITickable>(time);
		timer.addUpdatable(tickUpdate);
		
		// THEN UPDATE THINGS TO RENDER (render stuff)
		renderUpdate = new C2DUpdatables<IUpdatable>();
		timer.addUpdatable(renderUpdate);
		
		// THEN UPDATE THINGS TO PRESENT ON SCREEN (present things)
		presentUpdate = new C2DUpdatables<IUpdatable>();
		timer.addUpdatable(presentUpdate);		
	}
	
	public void start(Clear2DConfig config) {
		timer.setFps(config.fps);
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
	
}

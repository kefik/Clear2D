package cz.cuni.amis.clear2d.tests;

import org.junit.Test;

import cz.cuni.amis.clear2d.Clear2DConfig;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;
import cz.cuni.amis.clear2d.Clear2D;

public class Test01_FPS {
	
	public static class TimeDeltaLogger implements ITickable {

		@Override
		public void tick(C2DTime time) {
			System.out.println("Time delta: " + time.real.delta + " secs ~ " + (1 / time.real.delta) + " FPS");
		}
		
	}

	@Test
	public void test01() {
		TimeDeltaLogger logger = new TimeDeltaLogger();
		
		Clear2D.engine.tickUpdate.add(logger);
		
		Clear2D.engine.start(new Clear2DConfig());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		
		Clear2D.engine.stop();		
	}
	
	public static void main(String[] args) {
		Test01_FPS test = new Test01_FPS();
		
		test.test01();
	}
	
}

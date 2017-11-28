package cz.cuni.amis.clear2d.engine.time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import cz.cuni.amis.clear2d.engine.collections.C2DUpdatables;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

public class C2DTimer implements Runnable {
	
	public static final int DELTA_SERIES_BUFFER_SIZE = 30;
	
	private float fps;
	
	private float periodSecs;
	
	private long periodNanos;
	
	private boolean running = false;
	
	private Throwable exception;
	
	private Thread thread;
	
	private C2DUpdatables<IUpdatable> updates = new C2DUpdatables<IUpdatable>();
	
	private long lastNanos;
	
	private long nextRunNanos;
	
	private List<Long> deltaSeries = new ArrayList<Long>(DELTA_SERIES_BUFFER_SIZE);
	
	private long deltaSeriesTotal;
	
	public C2DTimer() {
		this(60, false);
	}
	
	public C2DTimer(float fps) {
		this(fps, false);
	}
	
	public C2DTimer(float fps, boolean autostart) {
		setFps(fps);
		if (autostart) this.start();
	}
	
	public float getFps() {
		return fps;
	}

	public void setFps(float fps) {
		this.fps = fps;
		this.periodSecs = 1.0f / fps;
		periodNanos = (long)(((double)1 / (double)fps) * 1000000000l);		
	}

	public boolean isRunning() {
		return running;
	}
	
	public Throwable getException() {
		return exception;
	}
	
	public void addUpdatable(IUpdatable updatable) {
		updates.add(updatable);
	}
	
	public void removeUpdatable(IUpdatable updatable) {
		updates.remove(updatable);
	}
	
	public boolean containsUpdatable(IUpdatable updatable) {
		return updates.contains(updatable);
	}
		
	@Override
	public void run() {
		running = true;
		
		try {
		
			long currNanos = 0;
			
			lastNanos = System.nanoTime();			
			
			while (running) {			
								
				nextRunNanos = periodNanos + (long)(deltaSeriesTotal / (double)DELTA_SERIES_BUFFER_SIZE);
				
				updates.update();
				
				while (running) {
					currNanos = System.nanoTime();
					
					long deltaNanos = currNanos - lastNanos; 
					
					nextRunNanos -= deltaNanos;
					
					lastNanos = currNanos;
					
					if (nextRunNanos <= 0) break;
					
					// pause for 0.2 milliseconds
					LockSupport.parkNanos(200000);					
				}
				
				if (deltaSeries.size() == DELTA_SERIES_BUFFER_SIZE) {
					deltaSeriesTotal -= deltaSeries.remove(0);
				}
				deltaSeries.add(nextRunNanos);
				deltaSeriesTotal += nextRunNanos;
			}
			
		} catch (Exception e) {
			this.exception = e;
			e.printStackTrace();
		} finally {
			running = false;
			thread = null;
		}	
		
	}
	
	public synchronized void start() {
		if (thread != null) return;
		thread = new Thread(this, "Clear2D Timer");		
		thread.start();
		
		int timeoutCount = 0;
		while (!running) {
			Thread.yield();
			++timeoutCount;
			if (timeoutCount > 10000) {
				new RuntimeException("Filed to start C2DTimer! Halting...").printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public synchronized void stop() {
		if (thread != null) {
			running = false;
			thread.interrupt();
			
			int timeoutCount = 0;
			while (thread != null) {
				Thread.yield();
				++timeoutCount;
				if (timeoutCount > 10000) {
					new RuntimeException("Filed to stop C2DTimer! Halting...").printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
}

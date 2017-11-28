package cz.cuni.amis.clear2d.engine.textures;

import java.awt.Graphics2D;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

public class AnimatedTexture implements IDrawable, ITickable {

	// ======
	// CONFIG
	// ======
	
	public float framePeriod;	
	public IDrawable[] frames;
	
	// =======
	// RUNTIME
	// =======
	
	public int frame;
	public float nextFrame;
	
	public boolean animating = false;
	
	public AnimatedTexture(int fps, IDrawable... frames) {
		// CONFIG
		
		this.framePeriod = 1.0f / (float)fps;
		this.frames = frames;
		
		// RUNTIME
		
		this.frame = 0;
		this.nextFrame = framePeriod;
		
		start();
	}
	
	public void start() {
		Clear2D.engine.tickUpdate.add(this);
		animating = true;
	}
	
	public void stop() {
		Clear2D.engine.tickUpdate.add(this);
		animating = false;
	}
	
	@Override
	public void tick(C2DTime time) {
		if (frames == null) return;
		if (frames.length == 0) return;
		
		nextFrame -= time.game.delta;
		if (nextFrame < 0) {
			frame = (frame + 1) % frames.length;
			nextFrame += framePeriod;
		}
	}

	@Override
	public void drawAt(Graphics2D g, float x, float y) {
		if (frames == null) return;
		if (frames.length == 0) return;
		
		if (frame > frames.length) frame = 0;		
		frames[frame].drawAt(g, x, y);
	}

	@Override
	public int getWidth() {
		if (frames != null && frame >= 0 && frame < frames.length) return frames[frame].getWidth();
		return 0;
	}

	@Override
	public int getHeight() {
		if (frames != null && frame >= 0 && frame < frames.length) return frames[frame].getHeight();
		return 0;
	}

}

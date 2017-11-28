package cz.cuni.amis.clear2d.engine.components;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.Component;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.iface.IRenderable;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.time.C2DTime;
import cz.cuni.amis.clear2d.engine.math.Vector2;

public class CAnimation<TEXTURE extends IDrawable> extends Component implements IRenderable, ITickable {

	/**
	 * X,Y-offset of the animation wrt. to its owner.
	 */
	public Vector2 pos = new Vector2();
	
	/**
	 * Sprites of the animation.
	 */
	public TEXTURE[] textures;
	
	public float fps;
	
	public int currFrame;
	
	public float nextFrameSecs;
	
	public CAnimation() {		
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 */
	public CAnimation(SceneElement owner) {
		super(owner);
	}
	
	public void initAnimation(float fps, TEXTURE... textures) {
		if (fps < 0.001) throw new RuntimeException("Invalid FPS = " + fps);
		
		this.fps = fps;
		this.textures = textures;
		
		nextFrameSecs = 1.0f / fps;
		currFrame = 0;
	}
	
	protected void notifyDo(Event event, Object... params) {
		if (textures == null || textures.length <= 0) return;
		
		if (event == Events.TICK) tick((C2DTime)params[0]);
		if (event == Events.RENDER) render((Camera)params[0]);
	}
	
	@Override
	public void render(Camera camera) {
		if (currFrame < textures.length) {
			camera.draw(pos.x, pos.y, textures[currFrame]);
		}
	}
	
	@Override
	public void tick(C2DTime time) {
		nextFrameSecs -= time.game.delta;
		if (nextFrameSecs <= 0) {
			currFrame += 1;
			currFrame %= textures.length;
			nextFrameSecs += 1.0f / fps;
		}
	}
	
}

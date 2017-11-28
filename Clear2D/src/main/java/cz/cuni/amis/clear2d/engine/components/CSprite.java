package cz.cuni.amis.clear2d.engine.components;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;

public class CSprite<TEXTURE extends IDrawable> extends CRender {

	/**
	 * Texture to draw. If null, does not render anything.
	 */
	public TEXTURE texture;
	
	public CSprite() {		
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 */
	public CSprite(SceneElement owner) {
		super(owner);
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 * @param drawable
	 */
	public CSprite(SceneElement owner, TEXTURE drawable) {
		super(owner);
		this.texture = drawable;
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 * @param drawable
	 * @param x initial dX
	 * @param y initial dY
	 */
	public CSprite(SceneElement owner, TEXTURE drawable, float x, float y) {
		super(owner);
		this.texture = drawable;
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public CSprite(TEXTURE drawable) {
		this.texture = drawable;
	}
	
	@Override
	public void render(Camera camera) {
		if (texture != null) {
			camera.draw(pos.x, pos.y, texture);
		}
	}
	
}

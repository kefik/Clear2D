package cz.cuni.amis.clear2d.engine;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.iface.IRender;
import cz.cuni.amis.clear2d.engine.math.Vector2;

public class Camera implements IRender {
	
	public final float camW;
	
	public final float camH;
	
	public Pivot pivot = Pivot.MIDDLE;
	
	/**
	 * TODO: investigate use of {@link AffineTransform} and {@link AffineTransformOp}, but that would probably be too slow to use.
	 */
	public Vector2 translation = new Vector2();
	
	/**
	 * Always has dimensions of {@link #camW} and {@link camH}.
	 * 
	 * Owned by this {@link Camera}, destroyed on {@link #die()}.
	 */
	public RenderTarget target;
	
	/**
	 * @param camW must remain int
	 * @param camH must remain int
	 */
	public Camera(int camW, int camH) {		
		this.camW = camW;
		this.camH = camH;
		target = new RenderTarget(camW, camH);
	}
	
	public float getNormX(float camX) {
		camX = pivot.getX(camX, camW);
		camX /= camW;
		
		return camX;
	}
	
	public float getNormY(float camY) {
		camY = pivot.getY(camY, camH);
		camY /= camH;
		
		return camY;
	}
	
	public float getCenterCamX() {
		return pivot.getX(0, camW);
	}
	
	public float getCenterCamY() {
		return pivot.getY(0, camW);
	}
	
	// ==================
	// CAMERA TRANSLATION
	// ==================
	
	public void pushTranslation(float x, float y) {
		translation.inAdd(x, y);		
	}
	
	public void pushTranslation(Vector2 v) {
		translation.inAdd(v);		
	}
	
	public void popTranslation(float x, float y) {
		translation.inSub(x, y);		
	}
	
	public void popTranslation(Vector2 v) {
		translation.inSub(v);		
	}
	
	// =======
	// IRender
	// =======
	
	@Override
	public void draw(Vector2 point, Image img) {
		if (point == null) return;
		drawInternal(point.x + translation.x, point.y + translation.y, img);
	}
	
	@Override
	public void draw(float x, float y, Image img) {
		drawInternal(x + translation.x, y + translation.y, img);
	}
		
	private void drawInternal(float camX, float camY, Image img) {
		camX = getNormX(camX);
		camY = getNormX(camY);
		
		target.draw(camX * target.widthF, camY * target.heightF, img);
	}
	
	@Override
	public void draw(Vector2 point, IDrawable img) {
		drawInternal(point.x + translation.x, point.y + translation.y, img);
	}
	
	@Override
	public void draw(float x, float y, IDrawable img) {
		drawInternal(x + translation.x, y + translation.y, img);
	}
	
	private void drawInternal(float camX, float camY, IDrawable img) {
		if (img == null) return;
		
		img.drawAt(target.graphics, camX, camY);
	}

	public void die() {
		if (target != null) {
			target.die();
			target = null;
		}		
	}
		
}

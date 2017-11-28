package cz.cuni.amis.clear2d.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.ReentrantLock;

import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.iface.IRender;
import cz.cuni.amis.clear2d.engine.math.Vector2;

/**
 * You do not want to render to this directly, use {@link Camera} instead that provides pivoting.
 * 
 * @author Jimmy
 */
public class RenderTarget implements IRender {

	public final int width;
	public final int height;
	
	public final float widthF;
	public final float heightF;
	
	private ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Owned by this {@link RenderTarget}, destroyed on {@link #die()}.
	 */
	public BufferedImage image;
	
	/**
	 * Owned by this {@link RenderTarget}, {@link Graphics2D#dispose()}ed on {@link #die()}.
	 */
	public Graphics2D graphics;
	
	public Color background = Color.BLACK;
	
	public RenderTarget(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.widthF = width;
		this.heightF = height;
		
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		this.graphics = image.createGraphics();
	}
	
	public void lock() {
		lock.lock();
	}
	
	public void unlock() {
		lock.unlock();
	}
	
	public void clear() {
		clear(background);
	}
	
	public void clear(Color color) {
		graphics.setColor(color);
		graphics.fillRect(0, 0, width, height);
	}
	
	@Override
	public void draw(Vector2 point, Image img) {
		if (point == null) return;
		draw(point.x, point.y, img);
	}
	
	@Override
	public void draw(float x, float y, Image img) {
		if (img == null) return;
		graphics.drawImage(image, (int)x, (int)y, img.getWidth(null), img.getHeight(null), null);
	}
	
	@Override	
	public void draw(Vector2 point, IDrawable img) {
		if (point == null) return;
		draw(point.x, point.y, img);
	}
	
	@Override
	public void draw(float x, float y, IDrawable img) {
		if (img == null) return;
		img.drawAt(graphics, x, y);
	}

	public void die() {
		if (graphics == null) {
			graphics.dispose();
			graphics = null;
		}
		image = null;		
	}
	
}

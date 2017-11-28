package cz.cuni.amis.clear2d.engine.iface;

import java.awt.Graphics2D;

public interface IDrawable {

	/**
	 * Draw 'instance' into 'g' at pixel-position [x;y]
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawAt(Graphics2D g, float x, float y);
	
	public int getWidth();
	
	public int getHeight();
	
}

package cz.cuni.amis.clear2d.engine.iface;

import java.awt.Image;

import cz.cuni.amis.clear2d.engine.math.Vector2;

public interface IRender {
	
	/**
	 * Render 'img' at 'point'-pixel.
	 * @param point pixel position to render 'img' at
	 * @param img what to render
	 */
	public void draw(Vector2 point, Image img);
	
	/**
	 * Render 'img' at ['x'-pixel;'y'-pixel]. 
	 * @param x
	 * @param y
	 * @param img what to render
	 */
	public void draw(float x, float y, Image img);
	
	/**
	 * Render 'img' at 'point'-pixel.
	 * @param point pixel position to render 'img' at
	 * @param img what to render
	 */
	public void draw(Vector2 point, IDrawable img);
	
	/**
	 * Render 'img' at ['x'-pixel;'y'-pixel].
	 * @param x
	 * @param y
	 * @param img what to render
	 */
	public void draw(float x, float y, IDrawable img);

}

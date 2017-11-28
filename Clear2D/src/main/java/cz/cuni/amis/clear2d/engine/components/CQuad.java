package cz.cuni.amis.clear2d.engine.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;

public class CQuad extends CRender implements IDrawable {

	protected BufferedImage quadImage;
	protected Graphics2D quadGraphics;
	
	protected Color colorFill;	
	protected Color colorOutline;
	
	public CQuad() {		
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 */
	public CQuad(SceneElement owner) {
		super(owner);
	}
	
	public void initQuad(int width, int height, Color colorFill, Color colorOutline) {
		setColor(colorFill);		
		setOutline(colorOutline);
		setSize(width, height);		
	}
	
	public void setColor(Color color) {
		if (color == null) {
			color = Color.BLACK;
		}		
		if (color.equals(colorFill)) return;
		colorFill = color;
		
		if (quadGraphics == null) return;
		
		drawColorFill();
		
		if (colorOutline != null) {
			setOutline(colorOutline);
		}
	}
	
	protected void drawColorFill() {
		if (colorFill == null) return;
		quadGraphics.setColor(colorFill);
		quadGraphics.fillRect(0, 0, quadImage.getWidth(), quadImage.getHeight());
	}
	
	public void setOutline(Color color) {		
		if (color != null) {
			if (color.equals(colorOutline)) return;
		}
		colorOutline = color;
		
		if (quadGraphics == null) return;
		
		drawColorOutline();
		
	}
	
	protected void drawColorOutline() {
		if (colorOutline == null) {
			quadGraphics.setColor(colorFill);
			quadGraphics.drawRect(0, 0, quadImage.getWidth()-1, quadImage.getHeight()-1);
		} else {
			quadGraphics.setColor(colorOutline);
			quadGraphics.drawRect(0, 0, quadImage.getWidth()-1, quadImage.getHeight()-1);
		}
	}
	
	public void setSize(int width, int height) {
		if (quadGraphics != null) quadGraphics.dispose();
		quadImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		quadGraphics = quadImage.createGraphics();
		drawColorFill();
		drawColorOutline();
	}
	
	public int getWidth() {
		return quadImage.getWidth();
	}
	
	public int getHeight() {
		return quadImage.getHeight();
	}
	
	public Color getColor() {
		return colorFill;
	}

	public Color getOutline() {
		return colorOutline;
	}

	@Override
	public void render(Camera camera) {
		camera.draw(pos.x, pos.y, this);		
	}

	@Override
	public void drawAt(Graphics2D g, float x, float y) {
		if (quadImage == null) return;
		g.drawImage(quadImage, (int)x, (int)y, null);			
	}
	
}

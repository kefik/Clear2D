package cz.cuni.amis.clear2d.engine.textures;

import java.awt.Graphics2D;

import cz.cuni.amis.clear2d.engine.iface.IDrawable;

public class Subtexture implements IDrawable {

	public String name;
	
	private Texture texture;
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int w;
	private int h;
	
	public Subtexture(Texture texture, int x1, int y1, int x2, int y2) {
		this(null, texture, x1, y1, x2, y2);
	}

	public Subtexture(String name, Texture texture, int x1, int y1, int x2, int y2) {
		this.name = name;
		this.texture = texture;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.w = x2 - x1;
		this.h = y2 - y1;
	}

	@Override
	public void drawAt(Graphics2D g, float x, float y) {
		if (texture == null || texture.image == null) return;
		g.drawImage(texture.image, (int)x, (int)y, (int)(x+w), (int)(y+h), x1, y1, x2, y2, null);
	}
	
	@Override
	public String toString() {
		return "Subtexture[name = " + name + ", x1 = " + x1 + ", y1 = " + y1 + ", x2 = " + x2 + ", y2 = " + y2 + ", w = " + w + ", h = " + h + "]";
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}
	
}

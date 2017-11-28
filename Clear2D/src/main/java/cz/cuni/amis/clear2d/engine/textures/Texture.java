package cz.cuni.amis.clear2d.engine.textures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import cz.cuni.amis.clear2d.engine.iface.IDrawable;

public class Texture implements IDrawable {

	protected Image image;
	
	protected int width;
	
	protected int height;
	
	protected Texture() {
		image = null;
	}
	
	public Texture(File imageFile) {
		if (imageFile == null) {
			throw new RuntimeException("imageFile == null, invalid");
		}
		try {
			setImage(ImageIO.read(imageFile));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load image from file: " + imageFile.getAbsolutePath());
		}
	}
	
	public Texture(InputStream imageInputStream) {
		if (imageInputStream == null) {
			throw new RuntimeException("imageInputStream == null, invalid");
		}
		try {
			setImage(ImageIO.read(imageInputStream));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load image from the supplied input stream.");
		}		
	}

	public Texture(Image image) {
		setImage(image);		
	}
	
	public void setImage(Image image) {
		this.image = image;
		if (this.image == null) {
			width = 0;
			height = 0;
		} else {
			width = image.getWidth(null);
			height = image.getHeight(null);
		}
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	public void drawAt(Graphics2D g, float x, float y) {
		if (image == null) return;
		g.drawImage(image, (int)x, (int)y, null);		
	}
	
}

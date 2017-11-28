package cz.cuni.amis.clear2d.engine.fonts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.amis.clear2d.engine.textures.Subtexture;
import cz.cuni.amis.clear2d.engine.textures.Texture;
import cz.cuni.amis.clear2d.utils.ColorMulti;

public class FontAtlas extends Texture {

	private int glyphsX;
	private int glyphsY;
	private int glyphWidth;
	private int glyphHeight;
	
	private Map<Integer, Subtexture> glyphs;

	public FontAtlas() {
		super();
	}
	
	protected void init(BufferedImage image, int glyphsX, int glyphsY, String text, Color colorMulti) {
		if (colorMulti != null) {
			image = ColorMulti.multi(image, colorMulti);
		}
		init(image, glyphsX, glyphsY, text);
	}
	
	protected void init(BufferedImage image, int glyphsX, int glyphsY, String text) {
		setImage(image);
		
		this.glyphsX = glyphsX;
		this.glyphsY = glyphsY;
		
		if (image.getWidth() % glyphsX != 0) throw new RuntimeException("Font atlas image width not divisible with glyphsX. Font atlas image dimensions are " + image.getWidth() + "x" + image.getHeight() + "px, cannot cut out " + glyphsX + "x" + glyphsY + " glyphs.");
		if (image.getHeight() % glyphsY != 0) throw new RuntimeException("Font atlas image width not divisible with glyphsY. Font atlas image dimensions are " + image.getWidth() + "x" + image.getHeight() + "px, cannot cut out " + glyphsX + "x" + glyphsY + " glyphs.");
		
		this.glyphWidth = image.getWidth() / glyphsX;
		this.glyphHeight = image.getHeight() / glyphsY;
		
		int charNumber = 0;
		
		glyphs = new HashMap<Integer, Subtexture>();
				
		for (int y = 0; y < glyphsY; ++y) {
			if (charNumber >= text.length()) break;
			for (int x = 0; x < glyphsX; ++x) {
				if (charNumber >= text.length()) break;
				
				Subtexture glyphTex = new Subtexture("" + text.charAt(charNumber), this, x * glyphWidth, y * glyphHeight, x * glyphWidth + glyphWidth, y * glyphHeight + glyphHeight);
				int glyphOrd = (int)text.charAt(charNumber);
				
				glyphs.put(glyphOrd, glyphTex);
				
				++charNumber;
			}
		}
	}

	public int getGlyphsX() {
		return glyphsX;
	}

	public int getGlyphsY() {
		return glyphsY;
	}

	public int getGlyphWidth() {
		return glyphWidth;
	}

	public int getGlyphHeight() {
		return glyphHeight;
	}
	
	public Subtexture getGlyph(String c) {
		return glyphs.get((int)c.charAt(0));
	}
	
	public Subtexture getGlyph(char c) {
		return glyphs.get((int)c);
	}
	
}

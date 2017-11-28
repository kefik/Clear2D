package cz.cuni.amis.clear2d.engine.components;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.fonts.FontAtlas;
import cz.cuni.amis.clear2d.engine.textures.Subtexture;

/**
 * Bitmap font renderer component; font is specified via setting {@link #fontAtlas} to use for the text rendering; see {@link C2DFonts} for embedded 
 * bitmap fonts.
 */
public class CText extends CRender {
	
	public FontAtlas fontAtlas;
	
	public String text;
	
	/**
	 * Auto-computed every render. Call {@link #updateDimensions()} to recompute manually.
	 */
	public int textWidth;
	
	/**
	 * Auto-computed every render. Call {@link #updateDimensions()} to recompute manually.
	 */
	public int textHeight;

	public CText() {
		super();
	}
	
	public CText(SceneElement owner) {
		super(owner);
	}
	
	/**
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 */
	public CText(FontAtlas fontAtlas) {
		this.fontAtlas = fontAtlas;
	}
	
	/**
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 * @param text text to be displayed
	 */
	public CText(FontAtlas fontAtlas, String text) {
		this.fontAtlas = fontAtlas;
		this.text = text;
		updateDimensions();
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 */
	public CText(SceneElement owner, FontAtlas fontAtlas) {
		this(owner);
		this.fontAtlas = fontAtlas;
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 * @param text text to be displayed
	 */
	public CText(SceneElement owner, FontAtlas fontAtlas, String text) {
		this(owner);
		this.fontAtlas = fontAtlas;
		this.text = text;
		updateDimensions();
	}
	
	
	@Override
	public void render(Camera camera) {
		if (text == null || text.length() == 0) {
			textWidth = 0;
			textHeight = 0;
			return;
		}
		if (fontAtlas == null) return;
		
		String[] lines = text.split("\n");
	
		float dX = pos.x;
		float dY = pos.y;
		
		float maxWidth = 0;
		for (String line : lines) {
			for (int charNum = 0; charNum < line.length(); ++charNum) {
				char lineChar = line.charAt(charNum);
				
				Subtexture glyph = fontAtlas.getGlyph(lineChar);
				if (glyph != null) {
					camera.draw(dX, dY, glyph);
				}
				
				dX += fontAtlas.getGlyphWidth();
			}
			
			if (dX - pos.x > maxWidth) maxWidth = dX - pos.x;
			
			dX = pos.x;
			dY += fontAtlas.getGlyphHeight();
		}
		
		textWidth  = (int)maxWidth;
		textHeight = (int)(dY - pos.y);
	}
	
	public void updateDimensions() {
		if (text == null) {
			textWidth = 0;
			textHeight = 0;
			return;
		}
		String[] lines = text.split("\n");
	
		int maxWidth = 0;
		for (String line : lines) {
			int width = fontAtlas.getGlyphWidth() * line.length();
			if (width > maxWidth) maxWidth = width;
		}
		
		textWidth  = maxWidth;
		textHeight = lines.length * fontAtlas.getGlyphHeight();
	}
	
	
	
}

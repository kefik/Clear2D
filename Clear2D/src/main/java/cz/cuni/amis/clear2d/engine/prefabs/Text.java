package cz.cuni.amis.clear2d.engine.prefabs;

import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.components.CText;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.fonts.FontAtlas;

/**
 * Bitmap font renderer using {@link CText}; font is specified via setting {@link #fontAtlas} to use for the text rendering; see {@link C2DFonts} for embedded 
 * bitmap fonts.
 */
public class Text extends SceneElement {
		
	public final CText cText;
	
	public Text() {
		this(null);
	}
	
	/**
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 */
	public Text(FontAtlas fontAtlas) {
		this(fontAtlas, null);
	}
	
	/**
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 * @param text text to be displayed
	 */
	public Text(FontAtlas fontAtlas, String text) {
		cText = new CText(this, fontAtlas, text);
	}
	
	@Override
	public String toString() {
		return "Text";
	}
	
}

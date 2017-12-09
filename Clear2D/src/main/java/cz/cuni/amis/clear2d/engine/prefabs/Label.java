package cz.cuni.amis.clear2d.engine.prefabs;

import java.awt.Color;

import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.components.CLabel;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.fonts.FontAtlas;

/**
 * Bitmap font renderer using {@link CLabel}; font is specified via setting cLabel.cText to use for the text rendering; see {@link C2DFonts} for embedded 
 * bitmap fonts.
 */
public class Label extends SceneElement {
		
	public final CLabel cLabel;
		
	/**
	 * @param fontAtlas see {@link C2DFonts} for default fonts; do not forget to {@link C2DFonts#init()}...
	 * @param text text to be displayed
	 */
	public Label(FontAtlas fontAtlas, String text, Color backgroundColor, Color outlineColor) {
		cLabel = new CLabel(this);
		cLabel.initLabel(fontAtlas, text, backgroundColor, outlineColor);
	}
	
	@Override
	public String toString() {
		return "Label";
	}
	
}

package cz.cuni.amis.clear2d.engine.fonts;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Catalogue of fonts of the common font family and stroke.
 * 
 * Use {@link #registerAtlas(int, Color, FontAtlas)} to file a {@link FontAtlas}, use {@link #getAtlas(int, Color)} to retrieve it.
 * @author Jimmy
 */
public class Font {
	
	public final String familyName;
	
	/**
	 * Size =&gt; Color =&gt; {@link FontAtlas}.
	 */
	public Map<Integer, Map<Color, FontAtlas>> atlases = new HashMap<Integer, Map<Color, FontAtlas>>();
	
	public Font(String familyName) {
		this.familyName = familyName;
	}
	
	public void registerAtlas(int size, Color color, FontAtlas atlas) {
		getColors(size, true).put(color, atlas);
	}
	
	public boolean hasAtlas(int size, Color color) {
		Map<Color, FontAtlas> colors = getColors(size);
		if (colors == null) return false;
		return colors.containsKey(color);
	}
	
	public FontAtlas getAtlas(int size, Color color) {
		Map<Color, FontAtlas> colors = getColors(size);
		if (colors == null) return null;
		return colors.get(color);
	}
	
	protected Map<Color, FontAtlas> getColors(int size) {
		return atlases.get(size);
	}
	
	protected Map<Color, FontAtlas> getColors(int size, boolean autoCreate) {
		Map<Color, FontAtlas> result = getColors(size);
		if (result != null) return result;
		
		result = new HashMap<Color, FontAtlas>();
		atlases.put(size, result);
		
		return result;
	}

}

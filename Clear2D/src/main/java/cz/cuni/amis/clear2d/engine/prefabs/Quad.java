package cz.cuni.amis.clear2d.engine.prefabs;

import java.awt.Color;

import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.components.CQuad;
import cz.cuni.amis.clear2d.engine.components.CSprite;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.textures.Subtexture;
import cz.cuni.amis.clear2d.engine.textures.Texture;

/**
 * Sprite (or more precisely {@link IDrawable}, e.g., {@link Texture} and {@link Subtexture}) renderer using {@link CSprite}.
 * @author Jimmy
 *
 * @param <TEXTURE>
 */
public class Quad extends SceneElement {
	
	public final CQuad cQuad; 
	
	public Quad() {
		cQuad = new CQuad(this);
	}
	
	public void init(int width, int height, Color colorFill, Color colorOutline) {
		cQuad.initQuad(width, height, colorFill, colorOutline);
	}
		
	@Override
	public String toString() {
		return "Sprite";
	}

}

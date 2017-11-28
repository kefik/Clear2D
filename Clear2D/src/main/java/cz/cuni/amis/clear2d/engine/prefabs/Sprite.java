package cz.cuni.amis.clear2d.engine.prefabs;

import cz.cuni.amis.clear2d.engine.SceneElement;
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
public class Sprite<TEXTURE extends IDrawable> extends SceneElement {
	
	public final CSprite cSprite; 
	
	public Sprite() {
		this(null);
	}
	
	public Sprite(TEXTURE drawable) {
		cSprite = new CSprite<TEXTURE>(this, drawable);		
	}
	
	@Override
	public String toString() {
		return "Sprite";
	}

}

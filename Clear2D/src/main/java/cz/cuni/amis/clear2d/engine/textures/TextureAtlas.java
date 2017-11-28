package cz.cuni.amis.clear2d.engine.textures;

import java.util.HashMap;
import java.util.Map;

public abstract class TextureAtlas extends Texture {
	
	protected Map<String, Subtexture> textures = new HashMap<String, Subtexture>();

	public Subtexture getSubtexture(String name) {
		return textures.get(name);
	}

}

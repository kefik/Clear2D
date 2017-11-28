package cz.cuni.amis.clear2d.engine.textures;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import cz.cuni.amis.clear2d.engine.textures.TextureAtlasXML.SubtextureXML;

public class TextureAtlasResource extends TextureAtlas {
	
	public TextureAtlasResource(InputStream atlasXMLInputStram, String atlasResourcePrefix) {
		super();
		load(atlasXMLInputStram, atlasResourcePrefix);
	}
	
	private void load(InputStream atlasXMLInputStram, String atlasResourcePrefix) {
		if (atlasXMLInputStram == null) {
			throw new RuntimeException("atlasXMLInputStram == null, invalid");
		}
		if (atlasResourcePrefix == null) {
			atlasResourcePrefix = "./";
		}
		
		TextureAtlasXML atlas = TextureAtlasXML.loadXML(atlasXMLInputStram);
		
		// LOAD SHEET
		String resourcePath = atlasResourcePrefix + "/" + atlas.imagePath;
		try {			
			setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(resourcePath)));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read resource: " + atlas.imagePath + " => " + resourcePath);
		}
		
		for (SubtextureXML subtextureXML : atlas.subtextures) {
			Subtexture subtexture = new Subtexture(subtextureXML.name, this, subtextureXML.x, subtextureXML.y, subtextureXML.x+subtextureXML.width, subtextureXML.y+subtextureXML.height);
			textures.put(subtexture.name, subtexture);
		}
	}
	
}

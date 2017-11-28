package cz.cuni.amis.clear2d.engine.textures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import cz.cuni.amis.clear2d.engine.textures.TextureAtlasXML.SubtextureXML;

public class TextureAtlasFile extends TextureAtlas {
	
	public TextureAtlasFile(File atlasXMLFile) {
		super();
		load(atlasXMLFile);
	}
	
	private void load(File atlasXMLFile) {
		if (atlasXMLFile == null) {
			throw new RuntimeException("atlasXMLFile == null, invalid");
		}
		
		TextureAtlasXML atlas = TextureAtlasXML.loadXML(atlasXMLFile);
		
		// LOAD SHEET
		File sheetFile = new File(atlasXMLFile.getParentFile(), atlas.imagePath);
		try {			
			setImage(ImageIO.read(sheetFile));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read: " + atlas.imagePath + " => " + sheetFile.getAbsoluteFile());
		}
		
		for (SubtextureXML subtextureXML : atlas.subtextures) {
			Subtexture subtexture = new Subtexture(subtextureXML.name, this, subtextureXML.x, subtextureXML.y, subtextureXML.x+subtextureXML.width, subtextureXML.y+subtextureXML.height);
			textures.put(subtexture.name, subtexture);
		}
	}

}

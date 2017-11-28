package cz.cuni.amis.clear2d.engine.fonts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class FontAtlasResource extends FontAtlas {
	
	public FontAtlasResource(InputStream fontAtlasXMLInputStram, String fontAtlasResourcePrefix) {
		this(fontAtlasXMLInputStram, fontAtlasResourcePrefix, null);
	}
	
	public FontAtlasResource(InputStream fontAtlasXMLInputStram, String fontAtlasResourcePrefix, Color colorMulti) {
		super();
		load(fontAtlasXMLInputStram, fontAtlasResourcePrefix, colorMulti);
	}
	
	private void load(InputStream fontAtlasXMLInputStram, String fontAtlasResourcePrefix, Color colorMulti) {
		if (fontAtlasXMLInputStram == null) {
			throw new RuntimeException("atlasXMLInputStram == null, invalid");
		}
		if (fontAtlasResourcePrefix == null) {
			fontAtlasResourcePrefix = "./";
		}
		
		FontAtlasXML atlas = FontAtlasXML.loadXML(fontAtlasXMLInputStram);
		
		// LOAD SHEET
		String resourcePath = fontAtlasResourcePrefix + "/" + atlas.imagePath;
		BufferedImage image;
		try {			
			image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(resourcePath));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read resource: " + atlas.imagePath + " => " + resourcePath);
		}
		
		// BUILD TEXTS
		StringBuilder text = new StringBuilder();
		for (String part : atlas.texts) {
			text.append(part);
		}
		
		// INIT
		init(image, atlas.glyphsX, atlas.glyphsY, text.toString(), colorMulti);			
	}
	
}

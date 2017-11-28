package cz.cuni.amis.clear2d.engine.fonts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FontAtlasFile extends FontAtlas {
	
	public FontAtlasFile(File fontAtlasXMLFile) {
		this(fontAtlasXMLFile, null);
	}
	
	public FontAtlasFile(File fontAtlasXMLFile, Color colorMulti) {
		super();
		load(fontAtlasXMLFile, colorMulti);
	}
	
	private void load(File fontAtlasXMLFile, Color colorMulti) {
		if (fontAtlasXMLFile == null) {
			throw new RuntimeException("atlasXMLFile == null, invalid");
		}
		
		FontAtlasXML atlas = FontAtlasXML.loadXML(fontAtlasXMLFile);
		
		// LOAD SHEET
		File sheetFile = new File(fontAtlasXMLFile.getParentFile(), atlas.imagePath);
		BufferedImage image;
		try {			
			image = ImageIO.read(sheetFile);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read: " + atlas.imagePath + " => " + sheetFile.getAbsoluteFile());
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

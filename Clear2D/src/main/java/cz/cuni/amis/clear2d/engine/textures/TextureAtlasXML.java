package cz.cuni.amis.clear2d.engine.textures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * To be used with ShoeBox...
 * @author Jimmy
 */
@XStreamAlias(value = "TextureAtlas")
public class TextureAtlasXML {
	
	@XStreamAlias(value = "SubTexture")
	public static class SubtextureXML {
		
		@XStreamAsAttribute
		public String name;
		
		@XStreamAsAttribute
		public int x;
		
		@XStreamAsAttribute
		public int y;
		
		@XStreamAsAttribute
		public int width;
		
		@XStreamAsAttribute
		public int height;
		
	}
	
	@XStreamAsAttribute
	public String imagePath;
	
	@XStreamImplicit(itemFieldName="SubTexture")
	public List<SubtextureXML> subtextures;
	
	public static TextureAtlasXML loadXML(File xmlFile) {
		if (xmlFile == null) {
			throw new IllegalArgumentException("'xmlFile' can't be null!");
		}
		try {
			return loadXML(new FileInputStream(xmlFile));
		} catch (Exception e) {
			throw new RuntimeException("Could not load file " + xmlFile.getAbsolutePath(), e);
		}
	}
	
	public static TextureAtlasXML loadXML(InputStream xmlStream) {
		if (xmlStream == null) {
			throw new IllegalArgumentException("'xmlStream' can't be null!");
		}		
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.alias(TextureAtlasXML.class.getAnnotation(XStreamAlias.class).value(), TextureAtlasXML.class);
		Object obj = xstream.fromXML(xmlStream);
		try {
			xmlStream.close();
		} catch (IOException e) {
		}
		if (obj == null || !(obj instanceof TextureAtlasXML)) {
			throw new RuntimeException("Stream didn't contain a xml with TextureAtlas.");
		}
		return (TextureAtlasXML)obj;
	}
		
}

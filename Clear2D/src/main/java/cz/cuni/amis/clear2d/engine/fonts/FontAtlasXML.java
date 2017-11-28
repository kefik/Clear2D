package cz.cuni.amis.clear2d.engine.fonts;

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

@XStreamAlias(value = "FontAtlas")
public class FontAtlasXML {
	
	@XStreamAsAttribute
	public String imagePath;
	
	@XStreamAsAttribute
	public int glyphsX;
	
	@XStreamAsAttribute
	public int glyphsY;
	
	@XStreamImplicit(itemFieldName="Text")
	public List<String> texts;
		
	public static FontAtlasXML loadXML(File xmlFile) {
		if (xmlFile == null) {
			throw new IllegalArgumentException("'xmlFile' can't be null!");
		}
		try {
			return loadXML(new FileInputStream(xmlFile));
		} catch (Exception e) {
			throw new RuntimeException("Could not load file " + xmlFile.getAbsolutePath(), e);
		}
	}
	
	public static FontAtlasXML loadXML(InputStream xmlStream) {
		if (xmlStream == null) {
			throw new IllegalArgumentException("'xmlStream' can't be null!");
		}		
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.alias(FontAtlasXML.class.getAnnotation(XStreamAlias.class).value(), FontAtlasXML.class);
		Object obj = xstream.fromXML(xmlStream);
		try {
			xmlStream.close();
		} catch (IOException e) {
		}
		if (obj == null || !(obj instanceof FontAtlasXML)) {
			throw new RuntimeException("Stream didn't contain a xml with FontAtlas.");
		}
		return (FontAtlasXML)obj;
	}
		
}

package cz.cuni.amis.clear2d.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import cz.cuni.amis.clear2d.engine.textures.TextureAtlasXML;
import cz.cuni.amis.clear2d.engine.textures.TextureAtlasXML.SubtextureXML;

public class TextureAtlasToEnum {

	public static void transform(File textureAtlasXMLFile, File targetEnumFile) {
		
		TextureAtlasXML atlas = TextureAtlasXML.loadXML(textureAtlasXMLFile);
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(targetEnumFile);
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("Failed to open for writing: " + targetEnumFile.getAbsolutePath());
		}
		PrintWriter writer = new PrintWriter(out);
		
		writer.println("public enum TextureAtlasEnum {");
		writer.println();
		
		boolean first = true;
		for (SubtextureXML subtexture : atlas.subtextures) {
			writer.print("  ");
			if (first) first = false;
			else writer.print(",");
			
			String name = Sanitize.variablify(subtexture.name);
			writer.print(name);
			writer.print("(\"");
			writer.print(subtexture.name);
			writer.println("\")");
		}		
		writer.println("  ;");
		
		writer.println();
		writer.println("  public final String texture;");
		writer.println();
		writer.println("  private TextureAtlasEnum(String name) {");
		writer.println("    texture = name;");
		writer.println("  }");
		writer.println();
		writer.println("}");
		
		
		try { writer.close(); } catch (Exception e) { }
		try { out.close(); } catch (Exception e) { }
		
	}
	
	public static void main(String[] args) {
		File source = new File("d:/Workspaces/MFF/GACR/GIT-DD4J/DarkDungeon4J-Visualization/src/main/resources/cz/dd4j/ui/gui/16x16-Indoor/sprites.xml");
		File target = new File("d:/Workspaces/MFF/GACR/GIT-DD4J/DarkDungeon4J-Visualization/src/main/java/TextureAtlasEnum.java");
		
		TextureAtlasToEnum.transform(source, target);
		
		System.out.println("---/// DONE ///---");
		
	}	
}

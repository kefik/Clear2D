package cz.cuni.amis.clear2d.engine.fonts;

import java.awt.Color;
import java.io.InputStream;

import cz.cuni.amis.clear2d.engine.prefabs.Text;

/**
 * Use {@link #init()} to initialize, then query {@link #inconsolata} and {@link #inconsolataBold} to retrieve {@link FontAtlas}es for the {@link Text}.
 * 
 * Available sizes: 8, 10, 12
 * 
 * Available colors: {@link Color#WHITE}, {@link Color#RED}, {@link Color#GREEN}, {@link Color#BLUE} and {@link Color#BLACK}
 * 
 * @author Jimmy
 */
public class C2DFonts {

	/**
	 * Once {@link #init()}ed, contains sizes: 8, 10, 12 and colors: {@link Color#WHITE}, {@link Color#RED}, {@link Color#GREEN}, {@link Color#BLUE} and {@link Color#BLACK}. 
	 */
	public static Font inconsolata;
	
	public static FontAtlas inconcolata_8px_white;
	public static FontAtlas inconcolata_8px_red;
	public static FontAtlas inconcolata_8px_green;
	public static FontAtlas inconcolata_8px_blue;
	public static FontAtlas inconcolata_8px_black;
	
	public static FontAtlas inconcolata_10px_white;
	public static FontAtlas inconcolata_10px_red;
	public static FontAtlas inconcolata_10px_green;
	public static FontAtlas inconcolata_10px_blue;
	public static FontAtlas inconcolata_10px_black;
	
	public static FontAtlas inconcolata_12px_white;
	public static FontAtlas inconcolata_12px_red;
	public static FontAtlas inconcolata_12px_green;
	public static FontAtlas inconcolata_12px_blue;
	public static FontAtlas inconcolata_12px_black;
	
	/**
	 * Once {@link #init()}ed, contains sizes: 8, 10, 12 and colors: {@link Color#WHITE}, {@link Color#RED}, {@link Color#GREEN}, {@link Color#BLUE} and {@link Color#BLACK}. 
	 */
	public static Font inconsolataBold;
	
	public static FontAtlas inconcolata_bold_8px_white;
	public static FontAtlas inconcolata_bold_8px_red;
	public static FontAtlas inconcolata_bold_8px_green;
	public static FontAtlas inconcolata_bold_8px_blue;
	public static FontAtlas inconcolata_bold_8px_black;
	
	public static FontAtlas inconcolata_bold_10px_white;
	public static FontAtlas inconcolata_bold_10px_red;
	public static FontAtlas inconcolata_bold_10px_green;
	public static FontAtlas inconcolata_bold_10px_blue;
	public static FontAtlas inconcolata_bold_10px_black;
	
	public static FontAtlas inconcolata_bold_12px_white;
	public static FontAtlas inconcolata_bold_12px_red;
	public static FontAtlas inconcolata_bold_12px_green;
	public static FontAtlas inconcolata_bold_12px_blue;
	public static FontAtlas inconcolata_bold_12px_black;
	
	private static boolean inited = false;
	
	public static void init() {
		if (inited) return;
		inited = true;
		
		String resourcePrefix;
		String atlasName;
		InputStream atlasStream;
		FontAtlas fontAtlas;
		
		// INCONSOLATA
		if (inconsolata == null) {
			inconsolata = new Font("Inconsolota");
			int[] sizes = new int[] { 8, 10, 12 };
			Color[] colors = new Color[] { Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.BLACK };
			for (int size : sizes) {
				resourcePrefix = "cz/cuni/amis/clear2d/engine/fonts/";
				atlasName      = "Inconsolata-" + size + "-Atlas.xml";
				String resource = resourcePrefix + atlasName;
				for (Color color : colors) {
					atlasStream = C2DFonts.class.getClassLoader().getResourceAsStream(resource);
					fontAtlas = new FontAtlasResource(atlasStream, resourcePrefix, color);		
					inconsolata.registerAtlas(size, color, fontAtlas);
				}
			}			
		}
		
		// INCONSOLATA BOLD
		if (inconsolataBold == null) {
			inconsolataBold = new Font("Inconsolota-Bold");
			int[] sizes = new int[] { 8, 10, 12 };
			Color[] colors = new Color[] { Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.BLACK };
			for (int size : sizes) {
				resourcePrefix = "cz/cuni/amis/clear2d/engine/fonts/";
				atlasName      = "Inconsolata-" + size + "-bold-Atlas.xml";
				String resource = resourcePrefix + atlasName;
				for (Color color : colors) {
					atlasStream = C2DFonts.class.getClassLoader().getResourceAsStream(resource);
					fontAtlas = new FontAtlasResource(atlasStream, resourcePrefix, color);		
					inconsolataBold.registerAtlas(size, color, fontAtlas);
				}
			}
		}
		
		inconcolata_8px_white = inconsolata.getAtlas(8, Color.WHITE);
		inconcolata_8px_red   = inconsolata.getAtlas(8, Color.RED);
		inconcolata_8px_green = inconsolata.getAtlas(8, Color.GREEN);
		inconcolata_8px_blue  = inconsolata.getAtlas(8, Color.BLUE);
		inconcolata_8px_black = inconsolata.getAtlas(8, Color.BLACK);
		
		inconcolata_10px_white = inconsolata.getAtlas(10, Color.WHITE);
		inconcolata_10px_red   = inconsolata.getAtlas(10, Color.RED);
		inconcolata_10px_green = inconsolata.getAtlas(10, Color.GREEN);
		inconcolata_10px_blue  = inconsolata.getAtlas(10, Color.BLUE);
		inconcolata_10px_black = inconsolata.getAtlas(10, Color.BLACK);
		
		inconcolata_12px_white = inconsolata.getAtlas(12, Color.WHITE);
		inconcolata_12px_red   = inconsolata.getAtlas(12, Color.RED);
		inconcolata_12px_green = inconsolata.getAtlas(12, Color.GREEN);
		inconcolata_12px_blue  = inconsolata.getAtlas(12, Color.BLUE);
		inconcolata_12px_black = inconsolata.getAtlas(12, Color.BLACK);
		
		inconcolata_bold_8px_white = inconsolataBold.getAtlas(8, Color.WHITE);
		inconcolata_bold_8px_red   = inconsolataBold.getAtlas(8, Color.RED);
		inconcolata_bold_8px_green = inconsolataBold.getAtlas(8, Color.GREEN);
		inconcolata_bold_8px_blue  = inconsolataBold.getAtlas(8, Color.BLUE);
		inconcolata_bold_8px_black = inconsolataBold.getAtlas(8, Color.BLACK);
		
		inconcolata_bold_10px_white = inconsolataBold.getAtlas(10, Color.WHITE);
		inconcolata_bold_10px_red   = inconsolataBold.getAtlas(10, Color.RED);
		inconcolata_bold_10px_green = inconsolataBold.getAtlas(10, Color.GREEN);
		inconcolata_bold_10px_blue  = inconsolataBold.getAtlas(10, Color.BLUE);
		inconcolata_bold_10px_black = inconsolataBold.getAtlas(10, Color.BLACK);
		
		inconcolata_bold_12px_white = inconsolataBold.getAtlas(12, Color.WHITE);
		inconcolata_bold_12px_red   = inconsolataBold.getAtlas(12, Color.RED);
		inconcolata_bold_12px_green = inconsolataBold.getAtlas(12, Color.GREEN);
		inconcolata_bold_12px_blue  = inconsolataBold.getAtlas(12, Color.BLUE);	
		inconcolata_bold_12px_black = inconsolataBold.getAtlas(12, Color.BLACK);
		
		if (inconcolata_8px_white  == null) throw new RuntimeException("Failed to load inconcolata_8px_white!");
		if (inconcolata_8px_red    == null) throw new RuntimeException("Failed to load inconcolata_8px_red!");
		if (inconcolata_8px_green  == null) throw new RuntimeException("Failed to load inconcolata_8px_green!");
		if (inconcolata_8px_blue   == null) throw new RuntimeException("Failed to load inconcolata_8px_blue!");
		if (inconcolata_8px_black  == null) throw new RuntimeException("Failed to load inconcolata_8px_black!");
		if (inconcolata_10px_white == null) throw new RuntimeException("Failed to load inconcolata_10px_white!");
		if (inconcolata_10px_red   == null) throw new RuntimeException("Failed to load inconcolata_10px_red!");
		if (inconcolata_10px_green == null) throw new RuntimeException("Failed to load inconcolata_10px_green!");
		if (inconcolata_10px_blue  == null) throw new RuntimeException("Failed to load inconcolata_10px_blue!");
		if (inconcolata_10px_black == null) throw new RuntimeException("Failed to load inconcolata_10px_black!");
		if (inconcolata_12px_white == null) throw new RuntimeException("Failed to load inconcolata_12px_white!");
		if (inconcolata_12px_red   == null) throw new RuntimeException("Failed to load inconcolata_12px_red!");
		if (inconcolata_12px_green == null) throw new RuntimeException("Failed to load inconcolata_12px_green!");
		if (inconcolata_12px_blue  == null) throw new RuntimeException("Failed to load inconcolata_12px_blue!");
		if (inconcolata_12px_black == null) throw new RuntimeException("Failed to load inconcolata_12px_black!");
		
		if (inconcolata_bold_8px_white  == null) throw new RuntimeException("Failed to load inconcolata_bold_8px_white!");
		if (inconcolata_bold_8px_red    == null) throw new RuntimeException("Failed to load inconcolata_bold_8px_red!");
		if (inconcolata_bold_8px_green  == null) throw new RuntimeException("Failed to load inconcolata_bold_8px_green!");
		if (inconcolata_bold_8px_blue   == null) throw new RuntimeException("Failed to load inconcolata_bold_8px_blue!");
		if (inconcolata_bold_8px_black  == null) throw new RuntimeException("Failed to load inconcolata_bold_8px_black!");
		if (inconcolata_bold_10px_white == null) throw new RuntimeException("Failed to load inconcolata_bold_10px_white!");
		if (inconcolata_bold_10px_red   == null) throw new RuntimeException("Failed to load inconcolata_bold_10px_red!");
		if (inconcolata_bold_10px_green == null) throw new RuntimeException("Failed to load inconcolata_bold_10px_green!");
		if (inconcolata_bold_10px_blue  == null) throw new RuntimeException("Failed to load inconcolata_bold_10px_blue!");
		if (inconcolata_bold_10px_black == null) throw new RuntimeException("Failed to load inconcolata_bold_10px_black!");
		if (inconcolata_bold_12px_white == null) throw new RuntimeException("Failed to load inconcolata_bold_12px_white!");
		if (inconcolata_bold_12px_red   == null) throw new RuntimeException("Failed to load inconcolata_bold_12px_red!");
		if (inconcolata_bold_12px_green == null) throw new RuntimeException("Failed to load inconcolata_bold_12px_green!");
		if (inconcolata_bold_12px_blue  == null) throw new RuntimeException("Failed to load inconcolata_bold_12px_blue!");;
		if (inconcolata_bold_12px_black == null) throw new RuntimeException("Failed to load inconcolata_bold_12px_black!");
		
	}
	
}

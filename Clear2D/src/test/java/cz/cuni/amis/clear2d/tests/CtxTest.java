package cz.cuni.amis.clear2d.tests;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.Clear2DConfig;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;

public class CtxTest {

	public static Clear2D engine;

	public static void init() {
		Clear2DConfig cfg = new Clear2DConfig();
		cfg.fps = 30;
		
		C2DFonts.init();
		
		engine = Clear2D.engine;		
		engine.start(cfg);		
	}
	
	public static void die() {
		engine.stop();
		engine = null;
	}
	
}

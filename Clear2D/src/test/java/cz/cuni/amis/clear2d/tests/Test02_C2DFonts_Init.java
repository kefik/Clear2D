package cz.cuni.amis.clear2d.tests;

import org.junit.Test;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;

public class Test02_C2DFonts_Init {

	@Test
	public void test() {
		C2DFonts.init();
		System.out.println("---/// ALL FONTS LOADED SUCCESSFULLY ///---");
	}
	
	public static void main(String[] args) {
		new Test02_C2DFonts_Init().test();
	}
	
}

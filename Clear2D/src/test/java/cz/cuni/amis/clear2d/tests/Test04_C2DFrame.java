package cz.cuni.amis.clear2d.tests;

import java.awt.Color;

import cz.cuni.amis.clear2d.engine.C2DFrame;
import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.RenderTarget;
import cz.cuni.amis.clear2d.engine.SceneRoot;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.prefabs.FPS;
import cz.cuni.amis.clear2d.engine.prefabs.Text;

public class Test04_C2DFrame {

	// Cannot be a real test...
	//@Test
	public void test() {
		CtxTest.init();
		
		C2DFrame frame = new C2DFrame("C2DFrame");
		
		Camera camera = frame.panel.scene.camera;
		RenderTarget target = frame.panel.scene.camera.target;
		target.background = Color.WHITE;
		
		SceneRoot root =  frame.panel.scene.root;
		
		FPS fps = new FPS();
		fps.pos.x = 10;
		fps.pos.y = 10;
		root.addChild(fps);
		
		Text text = new Text(C2DFonts.inconcolata_10px_black, "0123456789\nABCDEFGHIJKLMNOPQRSTUVWXYZ\nabcdefghijklmnopqrstuvwxyz\n!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
		text.pos.x = 10;
		text.pos.y = 30;		
		root.addChild(text);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Test04_C2DFrame().test();		
	}
	
}

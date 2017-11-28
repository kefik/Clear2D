package cz.cuni.amis.clear2d.engine;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class C2DFrame extends JFrame {
	
	/**
	 * Auto-generated... 
	 */
	private static final long serialVersionUID = 7800175102914230508L;
	
	public C2DPanelStandalone panel;
	
	public C2DFrame() {
		this("C2DFrame");
	}
	
	public C2DFrame(int panelWidth, int panelHeight) {
		this("C2DFrame", panelWidth, panelHeight);
	}
	
	public C2DFrame(String title) {
		this(title, 800, 600);
	}
	
	public C2DFrame(String title, int panelWidth, int panelHeight) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: determine correctly
		int extraW = 16;
		int extraH = 39;		
		setSize(panelWidth+extraW, panelHeight+extraH);
		
		panel = new C2DPanelStandalone(panelWidth, panelHeight, Color.BLACK);
		add(panel);
	}

	public void die() {
		panel.die();
	}

}

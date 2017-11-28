package cz.cuni.amis.clear2d.engine;

import java.awt.Color;

import cz.cuni.amis.clear2d.Clear2D;

public class C2DPanelStandalone extends C2DPanel {

	/**
	 * Auto-generated.
	 */
	private static final long serialVersionUID = 2090634921541538138L;
	
	public final Scene scene;
	
	/**
	 * @param width width of the camera we will be rendering into; we also assume the panel will have this width (can be changed later)
	 * @param height height of the camera we will be rendering into; we also assume the panel will have this width (can be changed later)
	 * @param background color of the background if we need a column-boxing
	 */
	public C2DPanelStandalone(int width, int height, Color background) {
		this(width, height, width, height, background);
	}
	
	/**
	 * @param panelWidth target size of the component displaying the contents of the camera
	 * @param panelHeight target size of the component displaying the contents of the camera
	 * @param camWidth width of the camera we will be rendering into
	 * @param camHeight height of the camera we will be rendering into
	 * @param background color of the background if we need a column-boxing
	 */
	public C2DPanelStandalone(int panelWidth, int panelHeight, int camWidth, int camHeight, Color background) {
		super(panelWidth, panelHeight, background, null);
		
		scene = new Scene(new Camera(camWidth, camHeight));
		setCamera(scene.camera);
		scene.setEnabled(true);
	}
	
	@Override
	public void setCamera(Camera camera) {
		super.setCamera(camera);
		if (scene != null) scene.camera = camera;
	}

	public void die() {
		super.die();
		scene.setEnabled(false);
		getCamera().die();
	}

}

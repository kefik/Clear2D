package cz.cuni.amis.clear2d.engine;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.engine.iface.IUpdatable;

/**
 * Just a panel; requires {@link Camera} to work with.
 * 
 * If you want something ready to be used out-of-the-box, use {@link C2DPanelStandalone}. 
 * 
 * @author Jimmy
 */
public class C2DPanel extends JPanel implements IUpdatable  {

	/**
	 * Auto-generated.
	 */
	private static final long serialVersionUID = -5832883655925759450L;

	private class PanelComponentListener extends ComponentAdapter {
		
		@Override
        public void componentResized(ComponentEvent e) {
        	if (imageGraphics != null) imageGraphics.dispose();        	
        	
        	int w = C2DPanel.this.getWidth();
        	int h = C2DPanel.this.getHeight();
        	
        	System.out.println("RESIZED: " + w + " x " + h);
        	
        	if (w <= 0 || h <= 0) {
        		image = null;
        		imageGraphics = null;
        		return;
        	}
        	
        	image = new BufferedImage(C2DPanel.this.getWidth(), C2DPanel.this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        	imageGraphics = image.createGraphics();        	
        }
        
        @Override
        public void componentHidden(ComponentEvent e) {
        }
        
        @Override
        public void componentShown(ComponentEvent e) {
        }
        
	}
	
	private BufferedImage image;
	
	private Graphics2D imageGraphics;
	
	private Camera camera;
	
	private boolean updating = false;
	
	public C2DPanel(int width, int height, Color background) {
		this(width, height, background, null);
	}
	
	public C2DPanel(int width, int height, Color background, Camera camera) {
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setAutoscrolls(false);
		setLayout(null);
		setBackground(background);
		
		if (camera != null) {
			setCamera(camera);
		}
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		imageGraphics = image.createGraphics();
		
		addComponentListener(new PanelComponentListener());		
		
		updateStart();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	public void update() {
		if (image == null || imageGraphics == null) return;
		if (camera == null) return;		
		RenderTarget source = camera.target;
		if (source == null) return;
		
		source.lock();
		
		try {
		
			// TODO: do this once and recalculate only on change...
			
			int sourceW = source.width;
			int sourceH = source.height;
			
			int targetW = image.getWidth();
			int targetH = image.getHeight();
			
			imageGraphics.setColor(getBackground());
			imageGraphics.fillRect(0, 0, targetW, targetH);
			
//			if (targetW == sourceW && targetH == sourceH) {
//				// DIRECT REWRITE
//				source.image.copyData(image.getRaster());
//				return;
//			}
			
			// FILTER
			
			float ratioW = (float)targetW / (float)sourceW;
			float ratioH = (float)targetH / (float)sourceH;
			
			float dX, dY;
			
			if (ratioW > ratioH) {
				ratioW = ratioH;			
				int sourceWbyRatio = (int)(sourceW * ratioW); 
				dX = targetW / 2 - sourceWbyRatio / 2;
				dY = 0;
			} else {
				ratioH = ratioW;
				int sourceHbyRatio = (int)(sourceH * ratioH);
				dX = 0;
				dY = targetH / 2 - sourceHbyRatio / 2;			
			}
			
			AffineTransform transform = AffineTransform.getScaleInstance(ratioW, ratioH);		
			transform.translate(dX / ratioW , dY / ratioH);
			
			AffineTransformOp transformOP = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			
			transformOP.filter(camera.target.image, image);
		} finally {
			source.unlock();
		}
		
		repaint();		
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	
	// ==================
	// VISIBILITY HANDLER
	// ==================
	
	/**
	 * TODO: if there is a way to get an event that "visibility" of this component OR some of its parent (mind reparenting!) changed; hook-up this m
	 *       method to that listener...
	 */
	protected void visibilityChanged() {
		boolean visible = isVisible();
		
		Container parent = getParent();
		while (visible && parent != null) {
			visible = visible && parent.isVisible();
			parent = parent.getParent();
		}
		
		if (visible) updateStart();
		else updateStop();
	}
	
	protected void updateStop() {
		if (!updating) return;
		updating = false;
		Clear2D.engine.presentUpdate.remove(C2DPanel.this);
	}
	
	protected void updateStart() {
		if (updating) return;
		updating = true;
		Clear2D.engine.presentUpdate.add(C2DPanel.this);
	}
	
	// ===========
	// TERMINATION
	// ===========
	
	public void die() {
		Clear2D.engine.presentUpdate.remove(this);
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			Clear2D.engine.presentUpdate.remove(this);
		} catch (Exception e) {			
		}
		super.finalize();
	}
	
	
}

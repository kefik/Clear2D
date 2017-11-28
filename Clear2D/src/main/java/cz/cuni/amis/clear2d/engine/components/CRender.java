package cz.cuni.amis.clear2d.engine.components;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.Component;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.iface.IRenderable;
import cz.cuni.amis.clear2d.engine.math.Vector2;

public class CRender extends Component implements IRenderable {

	/**
	 * X,Y-offset of the rendered stuff wrt. to its owner.
	 */
	public Vector2 pos = new Vector2();
	
	public CRender() {
		
	}
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 */
	public CRender(SceneElement owner) {
		super(owner);
	}
	
	protected void notifyDo(Event event, Object... params) {
		if (event == Events.RENDER) render((Camera)params[0]);
	}
	
	@Override
	public void render(Camera camera) {
	}
	
}

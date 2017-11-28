package cz.cuni.amis.clear2d.engine.events;

import cz.cuni.amis.clear2d.engine.Camera;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

public class Events {
	
	/**
	 * This scene element (in the parameter) has been enabled.
	 * Number of params: 1
	 * Params: {@link SceneElement} enabledElement 
	 * Propagation: enabled-only
	 */
	public static final Event SCENE_ELEMENT_ENABLED = new Event("SceneElementEnabled", true);

	/**
	 * This scene element (in the parameter) has been disabled.
	 * Number of params: 1
	 * Params: {@link SceneElement} disabledElement
	 * Propagation: enabled-only
	 */
	public static final Event SCENE_ELEMENT_DISABLED = new Event("SceneElementDisabled", true);
	
	/**
	 * The scene element (param1) parent's have been changed into (param2).
	 * Number of params: 3
	 * Params: {@link SceneElement} elementWhoseParentHasBeenChanged, {@link SceneElement} newParent, {@link SceneElement} oldParent
	 * Propagation: enabled + disabled 
	 */
	public static final Event SCENE_ELEMENT_PARENT_CHANGED = new Event("SceneElementParentChanged", false);
	
	/**
	 * Number of params: 1
	 * Params: {@link Camera} camera to render to
	 * Propagation: enabled-only, visible-only
	 */
	public static final Event RENDER = new Event("Render", true);
	
	/**
	 * Number of params: 1
	 * Params: {@link C2DTime} engine time
	 * Propagation: enabled-only
	 */
	public static final Event TICK = new Event("Tick", true);
		
}

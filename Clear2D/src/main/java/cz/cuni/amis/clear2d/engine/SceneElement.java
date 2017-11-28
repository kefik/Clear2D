package cz.cuni.amis.clear2d.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.cuni.amis.clear2d.engine.collections.ConcurrentList;
import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.iface.IControllable;
import cz.cuni.amis.clear2d.engine.iface.INotifiable;
import cz.cuni.amis.clear2d.engine.math.Vector2;
import cz.cuni.amis.clear2d.utils.Lazy;

public class SceneElement implements INotifiable, IControllable {
	
	/**
	 * Components of this {@link SceneElement}; there can be multiple components for a given class.
	 */
	protected Map<Class, ConcurrentList<Component>> components = new HashMap<Class, ConcurrentList<Component>>();
	
	/**
	 * Here we store the components in the order as they were added into the element.
	 */
	protected ConcurrentList<Component> componentsOrdered = new ConcurrentList<Component>();
	
	/**
	 * X,Y-position of the element.
	 */
	public Vector2 pos = new Vector2();
	
	/**
	 * Z-position within the parent; must be set via {@link #setZ(float)}.
	 */
	public float z = 0;
		
	/**
	 * Element's parent.
	 */
	public SceneElement parent = null;
	
	/**
	 * Should events be propagated (provided all parents are enabled as well)? (Unless they are for disabled as well...)
	 * READ-ONLY, use {@link #enable()} and {@link #disable()} to set this.
	 */
	public boolean enabled = true;
	
	/**
	 * Should this scene element be {@link Events#RENDER}ed (provided all parents are visible as well)?
	 */
	public boolean visible = true;
	
	/**
	 * Should the scene element be {@link Events#TICK}ing (provided all parents are ticking as well)?
	 */
	public boolean ticking = true;
		
	/**
	 * Sprite's children sorted according to {@link SceneElement#z}.
	 */
	public Lazy<ConcurrentList<SceneElement>> children = new Lazy<ConcurrentList<SceneElement>>() {

		@Override
		protected ConcurrentList<SceneElement> create() {
			return new ConcurrentList<SceneElement>() {
				public boolean add(SceneElement e) {
					synchronized(this) {
						ConcurrentIterator iterator = iterator();
						int index = -1;
						while (iterator.hasNext()) {
							++index;
							if (iterator.next().z > e.z) {
								// ADD!
								values.add(index, new Ref<SceneElement>(e));	
								if (index < consolidatedUpToIndex) ++consolidatedUpToIndex;
								iterator.dispose();
								return true;
							}
						}
						
						values.add(new Ref<SceneElement>(e));
						if (index < consolidatedUpToIndex) ++consolidatedUpToIndex;
						return true;
					}					
				};
			};
		}
		
	};
	
	// =======================
	// SCENE ELEMENT HIERARCHY
	// =======================
	
	/**
	 * Make 'newChild' to be a child of this {@link SceneElement}.
	 * 
	 * Cannot be called during {@link #render(Camera)}.
	 * 
	 * @param newChild
	 */
	public void addChild(SceneElement newChild) {
		newChild.setParent(this);
	}
	
	/**
	 * Remove 'child' from children; if the 'child' is not a direct child of this element, does nothing. 
	 * @param child
	 */
	public void removeChild(SceneElement child) {
		if (children.isNull()) return;
		if (children.get().contains(child)) {
			child.setParent(null);
		}
	}
	
	/**
	 * Changes parent of this {@link SceneElement}.
	 * 
	 * Cannot be called during {@link #render(Camera)}.
	 * @param newParent
	 */
	public void setParent(SceneElement newParent) {
		// ALREADY PARENT?
		if (parent == newParent) return;
		
		// DEREGISTER FROM THE PARENT
		SceneElement oldParent = parent;
		if (parent != null) {
			parent.children.get().remove(this);
		}		
		
		// REGISTER TO THE NEW PARENT
		parent = newParent;		
		if (parent != null) {
			parent.children.get().add(this);
		}
		
		// NOTIFY ABOUT THE CHANGE
		notify(Events.SCENE_ELEMENT_PARENT_CHANGED, this, newParent, oldParent);
	}
	
	/**
	 * Alters the {@link #z} position of the scene-element automatically resorting the position of this element within {@link #parent} {@link SceneElement#children}.
	 * @param newZ
	 */
	public void setZ(float newZ) {
		this.z = newZ;
		if (parent != null) {
			// RESORT!
			parent.children.get().remove(this);
			parent.children.get().add(this);
		}
	}
	
	@Override
	public void setEnabled(boolean state) {
		if (enabled == state) return;
		enabled = state;
		if (enabled) {
			notify(Events.SCENE_ELEMENT_ENABLED, this);
		} else {
			notifyDo(Events.SCENE_ELEMENT_DISABLED, this);	
		}
	}
	
	/**
	 * Returns "global position", i.e., absolute x;y within the scene.
	 * @return
	 */
	public Vector2 getGlobalPos() {
		Vector2 globalPos = new Vector2(pos);
		
		SceneElement parent = this.parent;
		while (parent != null) {
			globalPos.inAdd(parent.pos);
			parent = parent.parent;
		}
		
		return globalPos;			
	}
	
	/**
	 * Return relative-position from them global one.
	 * @param globalPos
	 * @return
	 */
	public Vector2 getLocalPos(Vector2 globalPos) {
		Vector2 localPos = new Vector2(globalPos);
		localPos.inSub(pos);
		
		SceneElement parent = this.parent;
		while (parent != null) {
			globalPos.inSub(parent.pos);
			parent = parent.parent;
		}
		
		return localPos;		
	}
		
	// ===================
	// COMPONENTS HANDLING
	// ===================
	
	public <COMPONENT> COMPONENT getComponent(Class<COMPONENT> componentClass) {
		return (COMPONENT) components.get(componentClass).iterator().next();
	}
	
	public <COMPONENT> Set<COMPONENT> getComponents(Class<COMPONENT> componentClass) {
		return (Set<COMPONENT>) components.get(componentClass);
	}
	
	public <COMPONENT extends Component> void addComponent(COMPONENT component) {
		ConcurrentList<Component> components = this.components.get(component.getClass());
		if (components == null) {
			components = new ConcurrentList<Component>();
			this.components.put(component.getClass(), components);
		}
		
		if (component.owner != null && component.owner != this) {
			component.owner.removeComponent(component);
		}
		component.owner = this;		
		components.add(component);
		componentsOrdered.add(component);
	}
	
	public void removeComponent(Component component) {
		ConcurrentList<Component> components = this.components.get(component.getClass());
		if (components == null) return;
		components.remove(component);
		componentsOrdered.remove(component);
	}
	
	public void removeComponents(Class<? extends Component> componentClass) {
		components.remove(componentClass);
		int i = 0;
		ConcurrentList<Component>.ConcurrentIterator iterator = componentsOrdered.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getClass() == componentClass) iterator.remove();
		}
		iterator.dispose();		
	}
	
	// ===============
	// EVENTS HANDLING
	// ===============

	@Override
	public void notify(Event event, Object... params) {
		if (event.propagateToEnabledElements) {
			// DO NOT PROPAGATE TO DISABLED SUB-TREE
			if (!enabled) return;
		}
				
		float dX = pos.x;
		float dY = pos.y;
		
		if (event == Events.RENDER) {
			// PREVENT RENDERING OF NON-VISIBLE ELEMENTS
			if (!visible) return;
			// PUSH CAMERA TRANSLATION
			((Camera)params[0]).pushTranslation(dX, dY);					
		} else
		if (event == Events.TICK) {
			// PREVENT TICKING OF NON-TICKING ELEMENTS
			if (!ticking) return;
		}
		
		notifyDo(event, params);
		
		if (event == Events.RENDER) {
			// POP CAMERA TRANSLATION
			((Camera)params[0]).popTranslation(dX, dY);
		}
	}
	
	private void notifyDo(Event event, Object... params) {
		// NOTIFY THIS SCENE ELEMENT
		handleEvent(event, params);
		
		// NOTIFY ALL COMPONENTS ABOUT THE EVENT
		ConcurrentList<Component>.ConcurrentIterator cmpIterator = componentsOrdered.iterator();
		while (cmpIterator.hasNext()) {
			cmpIterator.next().notify(event, params);
		}
		cmpIterator.dispose();
		
		// PROPAGATE THE EVENT TO CHILDREN
		if (!children.isNull()) {
			ConcurrentList<SceneElement>.ConcurrentIterator seIterator = children.get().iterator();
			while (seIterator.hasNext()) {
				seIterator.next().notify(event, params);
			}
			seIterator.dispose();			
		}
	}
	
	// =========================
	// RESPECTIVE EVENT HANDLERS
	// =========================
	
	/**
	 * Raised before any component or child receives this event.
	 * @param event
	 * @param params
	 */
	protected void handleEvent(Event event, Object... params) {
		// FOR SUBCLASSING...
	}
}

package cz.cuni.amis.clear2d.engine.events;

public class Event {
	
	private static int nextId = 1;
	
	private static Object mutex = new Object();
	
	public final int id;
	
	public final String name;
	
	public final boolean propagateToEnabledElements;
	
	Event(String name, boolean propagateToEnabledElements) {
		synchronized (mutex) {
			this.id = nextId++;
		}
		this.name = name;
		this.propagateToEnabledElements = propagateToEnabledElements;
		
		EventStore.registerEvent(this);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Event)) return false;
		Event other = (Event)obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		if (name != null) return "Event[" + name + "|" + id + "]";
		return "Event[" + id + "]";
	}

}

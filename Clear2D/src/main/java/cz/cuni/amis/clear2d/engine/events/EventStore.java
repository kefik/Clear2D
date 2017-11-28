package cz.cuni.amis.clear2d.engine.events;

import java.util.HashMap;
import java.util.Map;

public class EventStore {
	
	private static Map<String, Event> str2Event = new HashMap<String, Event>();
		
	private static Map<Integer, Event> int2Event = new HashMap<Integer, Event>();
	
	private static Object mutex = new Object();
	
	static void registerEvent(Event event) {
		synchronized(mutex) {
			if (int2Event.containsKey(event.id)) throw new RuntimeException("Clash of events for id: " + event + ", trying to register event of name: " + event.name);
			if (str2Event.containsKey(event.name)) throw new RuntimeException("Clash of events for name: " + event);

			int2Event.put(event.id, event);
			str2Event.put(event.name, event);							
		}	
	}
	
	public static Event get(String name) {
		return str2Event.get(name);
	}
	
	public static Event get(int id) {
		return int2Event.get(id);
	}	
	
}

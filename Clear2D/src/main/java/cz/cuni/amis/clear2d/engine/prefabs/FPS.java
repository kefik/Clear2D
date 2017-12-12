package cz.cuni.amis.clear2d.engine.prefabs;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.components.CText;
import cz.cuni.amis.clear2d.engine.events.Event;
import cz.cuni.amis.clear2d.engine.events.Events;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.fonts.FontAtlas;
import cz.cuni.amis.clear2d.engine.time.C2DTime;

public class FPS extends SceneElement {

	static {
		C2DFonts.init();
	}
	
	public CText cText;
	
	public List<Float> timeDeltas = new ArrayList<Float>();
	
	public float timeDeltaSum = 0;
	
	public float fpsWindowSecs = 2;
	
	public FPS() {
		this(C2DFonts.inconcolata_12px_blue);
	}
	
	public FPS(FontAtlas font) {
		cText = new CText(this, font, "FPS: 0");
	}
	
	@Override
	public void handleEvent(Event event, Object... params) {
		if (event == Events.TICK) {
			if (fpsWindowSecs <= 0) fpsWindowSecs = 1;
			
			C2DTime time = (C2DTime)params[0];
			float timeDelta = time.real.delta;
			timeDeltas.add(timeDelta);
			timeDeltaSum += timeDelta;
			while (timeDeltaSum > fpsWindowSecs && timeDeltas.size() > 0) {
				float oldTimeDelta = timeDeltas.remove(0);
				timeDeltaSum -= oldTimeDelta;
			}
			if (timeDeltas.size() > 0) {
				cText.text = String.format("FPS: %.2f", (float)1 / (timeDeltaSum / timeDeltas.size())); 
			}
		}
	}
	
	@Override
	public String toString() {
		return "FPS";
	}
	
}

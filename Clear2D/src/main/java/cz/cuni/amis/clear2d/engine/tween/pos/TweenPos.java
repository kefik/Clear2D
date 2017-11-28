package cz.cuni.amis.clear2d.engine.tween.pos;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.engine.iface.ITickable;
import cz.cuni.amis.clear2d.engine.math.Vector2;
import cz.cuni.amis.clear2d.engine.time.C2DTime;
import cz.cuni.amis.clear2d.engine.tween.ITweenFunc;
import cz.cuni.amis.clear2d.engine.tween.TweenEase;

public class TweenPos implements ITickable {

	// ==========
	// DEFINITION
	// ==========
	
	private Vector2 from;
	private Vector2 to;	
	private Vector2 tweened;
	private TweenPosType tweenType;
	private float tweenValue;
	private ITweenFunc tweenFunc;
	
	// =======
	// RUNTIME
	// =======
	
	private Vector2 start;
	private Vector2 target;
	
	private float tweenTime = -1;
	
	private float c = 0;
	private float cSign = 1;
	
	private boolean ticking = false;
	
	// =========
	// CALLBACKS
	// =========
	
	public ITweenPosCallback onPlayed;
	public ITweenPosCallback onReversed;
	
	public TweenPos(Vector2 source, Vector2 target, TweenPosType type, float value, TweenEase ease) {
		this(source, target, type, value, ease.func);
	}
	
	public TweenPos(Vector2 source, Vector2 target, TweenPosType type, float value, ITweenFunc easeFunc) {
		to = new Vector2(target);
		tweened = source;
		tweenType = type;
		tweenValue = value;
		tweenFunc = easeFunc;
	}
	
	private void setTicking(boolean state) {
		if (ticking == state) return;
		ticking = state;
		if (ticking) Clear2D.engine.tickUpdate.add(this);
		else         Clear2D.engine.tickUpdate.remove(this);
	}
	
	public boolean isTweening() {
		return ticking;
	}
	
	public boolean isPaused() {
		return !ticking && (c > 0 && c < 1);
	}
	
	public void play() {
		if (from == null) from = new Vector2(tweened);
		start = from;
		target = to;
		cSign = 1;
		if (tweenTime < 0) tweenTime = tweenType.type.getTime(from, to, tweenValue);
		setTicking(true);
	}
	
	public void reverse() {
		if (from == null) from = new Vector2(tweened);
		start = from;
		target = to;
		cSign = -1;
		if (tweenTime < 0) tweenTime = tweenType.type.getTime(from, to, tweenValue);
		setTicking(true);
	}
	
	public void pause() {
		setTicking(false);
	}
	
	public void resume() {
		setTicking(true);
	}
	
	public float getPosition() {
		return c;
	}
	
	public float getTime() {
		return c * tweenTime;
	}

	@Override
	public void tick(C2DTime time) {
		c += cSign * time.game.delta / tweenTime;
		
		boolean end = false;
		ITweenPosCallback callback = null;
		
		if (c > 1) {
			c = 1;
			end = true;
			callback = onPlayed;
		} else
		if (c < 0) {
			c = 0;
			end = true;
			callback = onReversed;
		}
		
		tweened.assign(tweenFunc.getValue(start.x, target.x, c), tweenFunc.getValue(start.y, target.y, c));
		
		if (end) {
			setTicking(false);
			tweenTime = -1;
			if (callback != null) {
				callback.run(this);
			}
		}
	}
	
}

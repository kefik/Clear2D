package cz.cuni.amis.clear2d.engine.tween;

public enum TweenEase {

	LINEAR(
		new ITweenFunc() {
			@Override
			public float getValue(float source, float target, float c) {
				return source + (target - source) * c;
			}			
		}
	),
	
	LINEAR_BACK(
		new ITweenFunc() {
			@Override
			public float getValue(float source, float target, float c) {
				if (c <= 0.5) {
					return source + (target - source) * c * 2;	
				} else {
					return source + (target - source) * (1 - c);
				}					
			}			
		}
	),
	
	;
	
	
	
	
	public final ITweenFunc func;

	private TweenEase(ITweenFunc func) {
		this.func = func;
	}
	
}

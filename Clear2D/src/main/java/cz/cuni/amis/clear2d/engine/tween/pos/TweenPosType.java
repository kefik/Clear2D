package cz.cuni.amis.clear2d.engine.tween.pos;

import cz.cuni.amis.clear2d.engine.math.Vector2;

public enum TweenPosType {
	
	/**
	 * Tweening time is determined according to speed in "pixels/seconds".
	 */
	SPEED(
		new ITweenPosType() {

			@Override
			public float getTime(Vector2 from, Vector2 to, float speed) {
				return to.sub(from).norm() / speed;
			}
			
		}
	),
	
	/**
	 * Tweening time is set directly.
	 */
	TIME(
			new ITweenPosType() {

				@Override
				public float getTime(Vector2 from, Vector2 to, float time) {
					return time;
				}
				
			}
		);
	
	public final ITweenPosType type;
	
	private TweenPosType(ITweenPosType type) {
		this.type = type;
	}

}

package cz.cuni.amis.clear2d.engine;

import cz.cuni.amis.clear2d.engine.math.Vector2;

/**
 * 0,0 ------ 0.5,0 ------ 1,0
 *  |            |          |
 *  |            |          |
 * 0,0.5 ---- 0.5,0.5 ---- 1,0.5
 *  |            |          |
 *  |            |          |
 * 0,1 ------ 0.5,1 ------ 1,1  
 * 
 * @author Jimmy
 */
public class Pivot {
	
	public static final Pivot TOP_LEFT = new Pivot(0, 0);
	
	public static final Pivot MIDDLE = new Pivot(0.5f, 0.5f);
	
	public static final Pivot BOTTOM_RIGHT = new Pivot(1.0f, 1.0f);
	
	public float x;
	public float y;
	
	public Pivot() {
		this(0,0);
	}
	
	public Pivot(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX(float x, float width) {
		return x - this.x * width;
	}
	
	public float getY(float y, float height) {
		return y - this.y * height;
	}
	
	public Vector2 get(Vector2 v, float width, float height) {
		return new Vector2(getX(v.x, width), getY(v.y, height));
	}

}

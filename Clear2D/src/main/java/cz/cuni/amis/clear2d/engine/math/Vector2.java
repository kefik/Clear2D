package cz.cuni.amis.clear2d.engine.math;

public class Vector2 {

	public float x;
	public float y;
	
	public Vector2() {
		x = 0;
		y = 0;
	}
	
	public Vector2(Vector2 source) {
		x = source.x;
		y = source.y;
	}
	
	public Vector2(float x, float y) {
		assign(x, y);
	}
	
	public void assign(Vector2 source) {
		this.x = source.x;
		this.y = source.y;
	}

	public void assign(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// =======
	// COMPUTE
	// =======
	
	public float normSq() {
		return x * x + y * y;
	}
	
	public float norm() {
		return (float)Math.sqrt(normSq());
	}
	
	public float length() {
		return norm();
	}
	
	public float dot(Vector2 other) {
		return x * other.x + y * other.y;
	}
	
	public float cross(Vector2 other) {
		return x * other.y - y * other.x;
	}
	
	// ===================== 
	// MANIPULATION IN PLACE
	// =====================
	
	public Vector2 inNegate() {
		x = -x;
		y = -y;
		return this;
	}
	
	public Vector2 inNormalize() {
		float norm = norm();
		x /= norm;
		y /= norm;
		return this;
	}
	
	public Vector2 inScale(float coef) {
		x *= coef;
		y *= coef;
		return this;
	}
	
	public Vector2 inAddX(float x) {
		this.x += x;
		return this;
	}
	
	public Vector2 inAddY(float y) {
		this.y += y;
		return this;
	}
	
	public Vector2 inSubX(float x) {
		this.x -= x;
		return this;
	}
	
	public Vector2 inSubY(float y) {
		this.y -= y;
		return this;
	}
	
	public Vector2 inMulX(float x) {
		this.x *= x;
		return this;
	}
	
	public Vector2 inMulY(float y) {
		this.y *= y;
		return this;
	}
	
	public Vector2 inAdd(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2 inSub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2 inMul(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2 inAdd(Vector2 other) {
		x += other.x;
		y += other.y;
		return this;
	}
	
	public Vector2 inSub(Vector2 other) {
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	public Vector2 inMul(Vector2 other) {
		x *= other.x;
		y *= other.y;
		return this;
	}
	
	// =======================
	// MANIPULATION GENERATIVE
	// =======================
	
	public Vector2 negate() {
		return new Vector2(-x, -y);
	}
	
	public Vector2 normalize() {
		float norm = norm();
		return new Vector2(x / norm, y / norm);
	}
	
	public Vector2 scale(float coef) {
		return new Vector2(x * coef, y * coef);
	}
	
	public Vector2 addX(float x) {
		return new Vector2(this.x + x, y);
	}
	
	public Vector2 addY(float y) {
		return new Vector2(x, this.y + y);
	}
	
	public Vector2 add(float x, float y) {
		return new Vector2(this.x + x, this.y + y);
	}
	
	public Vector2 sub(float x, float y) {
		return new Vector2(this.x - x, this.y - y);
	}
	
	public Vector2 mul(float x, float y) {
		return new Vector2(this.x * x, this.y * y);
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}
	
	public Vector2 sub(Vector2 other) {
		return new Vector2(x - other.x, y - other.y);
	}
	
	public Vector2 mul(Vector2 other) {
		return new Vector2(x * other.x, y * other.y);
	}
		
	@Override
	public String toString() {
		return "Vector2[" + x + ", " + y + "]";
	}
	
}

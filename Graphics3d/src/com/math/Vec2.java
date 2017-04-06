package com.math;

public class Vec2 {
	private float x;
	private float y;
	
	public Vec2() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2(Vec2 v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public void copy(Vec2 v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	// --------------------------
	public void add(Vec2 rhs) {
		this.x += rhs.x;
		this.y += rhs.y;
	}
	
	public static Vec2 add(Vec2 lhs, Vec2 rhs) {
		return new Vec2(lhs.x + rhs.x, lhs.y + rhs.y);
	}
	
	public void sub(Vec2 rhs) {
		this.x -= rhs.x;
		this.y -= rhs.y;
	}
	
	public static Vec2 sub(Vec2 lhs, Vec2 rhs) {
		return new Vec2(lhs.x - rhs.x, lhs.y - rhs.y);
	}

	public void scale(float scale) {
		this.x *= scale;
		this.y *= scale;
	}
	
	public static Vec2 scale(Vec2 lhs, float scale) {
		return new Vec2(lhs.x * scale, lhs.y * scale);
	}

	public float dot(Vec2 rhs) {
		return x * rhs.x + y * rhs.y;
	}
	
	public static float dot(Vec2 lhs, Vec2 rhs) {
		return lhs.x * rhs.x + lhs.y * rhs.y;
	}
	
	
	public float getLength() {
		return (float)(Math.sqrt(this.x*this.x + this.y * this.y));
	}
	
	public void normalize() {
		float length = getLength();
		if (length < MathUtil.EPSILON3) {
			return;
		}
		float length_inv = 1.0f / length;
		x *= length_inv;
		y *= length_inv;
	}
	
	public static Vec2 normalize(Vec2 v) {
		float length = v.getLength();
		return new Vec2(v.getX() / length, v.getY() / length);
	}
	
	// returns the cosine of the angle between wo vectors.
	public static float cosTh(Vec2 lhs, Vec2 rhs) {
		return dot(lhs, rhs) / (lhs.getLength() * rhs.getLength());
	}
	
	//-----------------------------
	
	public void print() {
		System.out.println("x: " + x + ", y: " + y);
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
//	public static void main(String[] args) {
//		Vec2 v1 = new Vec2(3, 5);
//		v1.print();
//		Vec2 v2 = new Vec2(1, 1);
//		v2.print();
//		
//		v2.add(v1);
//		v2.print();
//		
//		v1 = Vec2.add(v1, v2);
//		v1.print();
//	}
}









package com.math;

public class Vec3 {
	private float x;
	private float y;
	private float z;
	
	public Vec3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	public Vec3(Vec3 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public void copy(Vec3 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	//------------------------------------------------------
	public void add(Vec3 rhs) {
		this.x += rhs.x;
		this.y += rhs.y;
		this.z += rhs.z;
	}
	
	public static Vec3 add(Vec3 lhs, Vec3 rhs) {
		return new Vec3(lhs.x + rhs.x, lhs.y + rhs.y, lhs.z + rhs.z);
	}
	
	public void sub(Vec3 rhs) {
		this.x -= rhs.x;
		this.y -= rhs.y;
		this.z -= rhs.z;
	}
	
	public static Vec3 sub(Vec3 lhs, Vec3 rhs) {
		return new Vec3(lhs.x - rhs.x, lhs.y - rhs.y, lhs.z - rhs.z);
	}
	
	public void scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}
	
	public static Vec3 scale(Vec3 lhs, float scale) {
		return new Vec3(lhs.x * scale, lhs.y * scale, lhs.z * scale);
	}
	
	public float dot(Vec3 rhs) {
		return x * rhs.x + y * rhs.y + z * rhs.z;
	}
	
	public static float dot(Vec3 lhs, Vec3 rhs) {
		return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z;
	}
	
	public void cross(Vec3 rhs) {
		x =  ( (y * rhs.z) - (z * rhs.y) );
		y = -( (x * rhs.z) - (z * rhs.x) );
		z =  ( (x * rhs.y) - (y * rhs.x) ); 
	}
	
	public static Vec3 cross(Vec3 lhs, Vec3 rhs) {
		return new Vec3((lhs.y * rhs.z) - (lhs.z * rhs.y), 
				-((lhs.x * rhs.z) - (lhs.z * rhs.x)),
				(lhs.x * rhs.y) - (lhs.y * rhs.x));
	}
	
	public float getLength() {
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public void normalize() {
		float length = getLength();
		if (length < MathUtil.EPSILON3) {
			return;
		}
		float length_inv = 1.0f / length;
		x *= length_inv;
		y *= length_inv;
		z *= length_inv;
	}
	
	public static Vec3 normalize(Vec3 v) {
		float length = v.getLength();
		return new Vec3(v.x / length, v.y / length, v.z / length);
	}
	
	// returns the cosine of the angle between wo vectors.
	public static float cosTh(Vec3 lhs, Vec3 rhs) {
		return dot(lhs, rhs) / (lhs.getLength() * rhs.getLength());
	}
	
	//---------------------------------------------------------
	
	
	
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
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
}






















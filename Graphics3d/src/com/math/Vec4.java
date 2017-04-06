package com.math;

public class Vec4 {
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Vec4() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 1;
	}
	
	public Vec4(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	
	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4(Vec4 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}
	
	public void copy(Vec4 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}
	
	// -------------------------------
	public void add(Vec4 rhs) {
		this.x += rhs.x;
		this.y += rhs.y;
		this.z += rhs.z;
		this.w = 1;
	}
	
	public static Vec4 add(Vec4 lhs, Vec4 rhs) {
		return new Vec4(lhs.x + rhs.x, lhs.y + rhs.y, lhs.z + rhs.z);
	}
	
	public void sub(Vec4 rhs) {
		this.x -= rhs.x;
		this.y -= rhs.y;
		this.z -= rhs.z;
		this.w = 1;
	}
	
	public static Vec4 sub(Vec4 lhs, Vec4 rhs) {
		return new Vec4(lhs.x - rhs.x, lhs.y - rhs.y, lhs.z - rhs.z);
	}
	
	public void scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		this.w = 1;
	}
	
	public static Vec4 scale(Vec4 lhs, float scale) {
		return new Vec4(lhs.x * scale, lhs.y * scale, lhs.z * scale);
	}
	
	public float dot(Vec4 rhs) {
		return x * rhs.x + y * rhs.y + z * rhs.z;
	}
	
	public static float dot(Vec4 lhs, Vec4 rhs) {
		return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z;
	}
	
	public void cross(Vec4 rhs) {
		x =  ( (y * rhs.z) - (z * rhs.y) );
		y = -( (x * rhs.z) - (z * rhs.x) );
		z =  ( (x * rhs.y) - (y * rhs.x) ); 
		w = 1;
	}
	
	public static Vec4 cross(Vec4 lhs, Vec4 rhs) {
		return new Vec4((lhs.y * rhs.z) - (lhs.z * rhs.y), 
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
		w = 1;
	}
	
	public static Vec4 normalize(Vec4 v) {
		float length = v.getLength();
		return new Vec4(v.x / length, v.y / length, v.z / length);
	}
	
	// returns the cosine of the angle between wo vectors.
	public static float cosTh(Vec4 lhs, Vec4 rhs) {
		return dot(lhs, rhs) / (lhs.getLength() * rhs.getLength());
	}
	
	public void divByW() {
		this.x /= this.w;
		this.y /= this.w;
		this.z /= this.w;
	}
	
	public Vec3 getVec3ByW() {
		return new Vec3(this.x / this.w, this.y / this.w, this.z / this.w);
	}
	
	//----------------------------------
	
	
	
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
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
}






















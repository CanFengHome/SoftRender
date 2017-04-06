package com.math;

public class Cylindrical3D {
	private float r;
	private float theta;
	private float z;
	
	public Cylindrical3D() {
		this.r = 0;
		this.theta = 0;
		this.z = 0;
	}
	
	public Cylindrical3D(float r, float radians, float z) {
		this.r = r;
		this.theta = radians;
		this.z = z;
	}
	
	public Vec3 cylindrical3dToPoint3d() {
		float x = (float)(r * Math.cos(theta));
		float y = (float)(r * Math.sin(theta));
		return new Vec3(x, y, z);
	}
	
	public void point3dTocylindrical3d(Vec3 v) {
		r = (float)(Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY()));
		theta = (float)(Math.atan(v.getY() / v.getX()));
		z = v.getZ();
	}
	
	
	

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getTheta() {
		return theta;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}














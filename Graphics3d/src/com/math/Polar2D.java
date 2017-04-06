package com.math;

public class Polar2D {
	private float r;
	private float theta; // radians
	


	public Polar2D() {
		this.r = 0;
		this.theta = 0;
	}
	
	public Polar2D(float r, float radians) {
		this.r = r;
		this.theta = radians;
	}
	
	public Vec2 getPolar2dToPoint2d() {
		return new Vec2((float)(r * Math.cos(theta)), (float)(r * Math.sin(theta)));
	}
	
	public void point2dToPolar2d(Vec2 v) {
		this.r = v.getLength();
		this.theta = (float)(Math.atan(v.getY() / v.getX()));
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
}
















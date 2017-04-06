package com.math;

public class Spherical3D {
	private float p; // rho, the distance to the point from the origin
	private float theta; // the angle from the z-axis and the line segment o->p
	private float phi; // the angle from the projection if o->p onto the x-y plane and the x-axis
	
	public Spherical3D() {
		this.p = 0;
		this.theta = 0;
		this.phi = 0;
	}
	
	public Spherical3D(float p, float thetaRadians, float phiRadians) {
		this.p = p;
		this.theta = thetaRadians;
		this.phi = phiRadians;
	}
	
	public Vec3 spherical3dToPoint3d() {
		Vec3 result = new Vec3();
		float r = (float)(p * Math.sin(phi));
		result.setZ((float)(r*Math.cos(phi)));
		result.setX((float)(r*Math.cos(theta)));
		result.setY((float)(r*Math.sin(theta)));
		return result;
	}
	
	public void point3dToSpherical3d(Vec3 v) {
		p = v.getLength();
		this.theta = (float)(Math.atan(v.getY() / v.getX()));
		float r = (float)(Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY()));
		this.phi = (float)Math.asin(r / p);
	}
}



















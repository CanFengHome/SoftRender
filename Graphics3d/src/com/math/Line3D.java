package com.math;

public class Line3D {
	private Vec3 p0;
	private Vec3 p1;
	private Vec3 v;
	
	public void Lin3D(Vec3 ptStart, Vec3 ptEnd) {
		p0 = ptStart;
		p1 = ptEnd;
		v = Vec3.sub(ptEnd, ptStart);
	}
	
	public Vec3 getParmLine3d(float t) {
		float x = p0.getX() + v.getX() * t;
		float y = p0.getY() + v.getY() * t;
		float z = p0.getZ() + v.getZ() * t;
		return new Vec3(x, y, z);
	}
	
	

	public Vec3 getP0() {
		return p0;
	}

	public Vec3 getP1() {
		return p1;
	}

	public Vec3 getV() {
		return v;
	}
}









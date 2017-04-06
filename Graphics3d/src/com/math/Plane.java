package com.math;

public class Plane {
	Vec3 pos;
	Vec3 normal;
	
	public Plane(Vec3 pos, Vec3 normal) {
		this.pos = pos;
		this.normal = normal;
		this.normal.normalize();
	}
	
	public Vec3 getPos() {
		return pos;
	}
	public void setPos(Vec3 pos) {
		this.pos = pos;
	}
	public Vec3 getNormal() {
		return normal;
	}
	public void setNormal(Vec3 normal) {
		this.normal = normal;
	}
	
	public enum EPlanLine
	{
		NO_INTERSECT,
		INTERSECT_IN_SEGMENT,
		INTERSECT_OUT_SEGMENT,
		INTERSECT_EVERYWHERE
	}
}









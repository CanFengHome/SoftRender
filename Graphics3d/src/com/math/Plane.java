package com.math;

public class Plane {
	Vec3 pos;
	Vec3 normal;
	
	public Plane(Vec3 pos, Vec3 normal) {
		this(pos, normal, true);
	}
	
	public Plane(Vec3 pos, Vec3 normal, boolean normalize) {
		this.pos = pos;
		this.normal = normal;
		
		if (normalize == true) {
			this.normal.normalize();
		}
	}
	
	public float computePointInPlane3d(Vec3 pt) {
		Vec3 tempV = Vec3.sub(pt, pos);
		return Vec3.dot(normal, tempV);
	}
	
	public static PlaneLineIntersectParm getIntesetParmLine3dToPlane3d(Line3D line, Plane plane) {
		// first test of the line and the plane are parallel
		float plane_dot_line = Vec3.dot(line.getV(), plane.getNormal());
		if (Math.abs(plane_dot_line) <= MathUtil.EPSILON5) {
			float pointInPlane = plane.computePointInPlane3d(line.getP0());
			if (Math.abs(pointInPlane) < MathUtil.EPSILON5) {
				return new PlaneLineIntersectParm(PlaneLineIntersectParm.Relation.INTESECT_EVERYWHERE); // 重合
			} else {
				return new PlaneLineIntersectParm(PlaneLineIntersectParm.Relation.INTESECT_NONE); // 平行
			}
		}
		
		// from chapter 4 we know that we can solve for the t where 
		// intersection occurs by
		// a*(x0+vx*t) + b*(y0+vy*t) + c*(z0+vz*t) + d =0
		// t = -(a*x0 + b*y0 + c*z0 + d)/(a*vx + b*vy + c*vz)
		// x0,y0,z0, vx,vy,vz, define the line
		// d = (-a*xp0 - b*yp0 - c*zp0), xp0, yp0, zp0, define the point on the plane 
		float d = -plane.getNormal().getX() * plane.pos.getX() - plane.getNormal().getY() * plane.pos.getY() - plane.getNormal().getZ() * plane.pos.getZ();
		float t = -(plane.getNormal().getX() * line.getP0().getX() +  
				    plane.getNormal().getY() * line.getP0().getY() + 
				    plane.getNormal().getZ() * line.getP0().getZ() +
				    d) / (plane_dot_line);
		
		Vec3 crossPt = new Vec3(line.getP0().getX() + line.getV().getX() * t,
							    line.getP0().getY() + line.getV().getY() * t,
							    line.getP0().getZ() + line.getV().getZ() * t);
		
		if (t >= 0.0f && t <= 1.0f) {
			return new PlaneLineIntersectParm(PlaneLineIntersectParm.Relation.INTESECT_IN_SEGMENT, t, crossPt);
		} else {
			return new PlaneLineIntersectParm(PlaneLineIntersectParm.Relation.INTESECT_OUT_SEGMENT, t, crossPt);
		}
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









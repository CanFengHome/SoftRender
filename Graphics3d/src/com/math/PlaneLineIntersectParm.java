package com.math;

public class PlaneLineIntersectParm {
	public enum Relation {
		INTESECT_EVERYWHERE,
		INTESECT_NONE,
		INTESECT_IN_SEGMENT,
		INTESECT_OUT_SEGMENT
	}
	
	private Relation relation;
	private float t;
	private Vec3 crossPt;
	
	public PlaneLineIntersectParm(Relation relation) {
		this(relation, 0.0f, new Vec3());
	}
	
	public PlaneLineIntersectParm(Relation relation, float t, Vec3 crossPt) {
		this.relation = relation;
		this.t = t;
		this.crossPt = crossPt;
	}


	public Relation getRelation() {
		return relation;
	}


	public float getT() {
		return t;
	}


	public Vec3 getCrossPt() {
		return crossPt;
	}
}






















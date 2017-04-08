package com.math;


public class Line2DRelation
{
	public enum RelationType {
		INTERSECT_NONE,
		INTERSECT_IN_SEGMENT,
		INTERSECT_OUT_SEGMENT
	}
	
	private RelationType relation;
	private float t0;
	private float t1;
	private Vec2 crossPoint;
	
	public Line2DRelation(RelationType relation, float t0, float t1) {
		this.relation = relation;
		this.t0 = t0;
		this.t1 = t1;
		this.crossPoint = new Vec2();
	}
	
	public Line2DRelation(RelationType relation, float t0, float t1, Vec2 crossPoint) {
		this.relation = relation;
		this.t0 = t0;
		this.t1 = t1;
		this.crossPoint = crossPoint;
	}

	public RelationType getRelation() {
		return relation;
	}

	public float getT0() {
		return t0;
	}

	public float getT1() {
		return t1;
	}
	
	public Vec2 getCrossPoint() {
		return crossPoint;
	}
}
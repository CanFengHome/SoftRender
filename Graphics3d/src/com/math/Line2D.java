package com.math;

public class Line2D {
	private Vec2 p0;
	private Vec2 p1;
	private Vec2 v;
	
	public void Lin2D(Vec2 ptStart, Vec2 ptEnd) {
		p0 = ptStart;
		p1 = ptEnd;
		v = Vec2.sub(ptEnd, ptStart);
	}
	
	public Vec2 getParmLine2d(float t) {
		float x = p0.getX() + v.getX() * t;
		float y = p0.getY() + v.getY() * t;
		return new Vec2(x, y);
	}
	
	public static Line2DRelation calLine2dRelation(Line2D p1, Line2D p2) {
		float det_p1p2 = p1.v.getX() * p2.v.getY() - p1.v.getY() * p2.v.getX();
		
		// step 1: test for parallel lines, if the direction vectors are 
		// scalar multiples then the lines are parallel and can't possible
		// intersect
		if (Math.abs(det_p1p2) <= MathUtil.EPSILON5) {
			return new Line2DRelation(Line2DRelation.RelationType.INTERSECT_NONE, 0.0f, 0.0f);
		}
		
		// step 2: now compute the t1, t2 values for intersection
		// we have two lines of the form
		// p    = p0    +  v*t, specifically
		// p1   = p10   + v1*t1

		// p1.x = p10.x + v1.x*t1
		// p1.y = p10.y + v1.y*t1

		// p2 = p20 + v2*t2
		// p2   = p20   + v2*t2

		// p2.x = p20.x + v2.x*t2
		// p2.y = p20.y + v2.y*t2
		// solve the system when x1 = x2 and y1 = y2
		// explained in chapter 4
		float t1 = (p2.v.getX() * (p1.p0.getY() - p2.p0.getY()) - p2.v.getY() * (p1.p0.getX() - p2.p0.getX())) 
			      /det_p1p2;

		float t2 = (p1.v.getX() * (p1.p0.getY() - p2.p0.getY()) - p1.v.getY() * (p1.p0.getX() - p2.p0.getX()))
			      /det_p1p2;

		float x = p1.p0.getX() + p1.v.getX() * t1;
		float y = p1.p0.getY() + p1.v.getY() * t1;
		Vec2 crossPt = new Vec2(x, y);
		if ((t1 >= 0) && (t1 <= 1) && 
			(t2 >= 0) && (t2 <= 1))
		{
			return new Line2DRelation(Line2DRelation.RelationType.INTERSECT_IN_SEGMENT, t1, t2, crossPt);
		}
		else
		{
			return new Line2DRelation(Line2DRelation.RelationType.INTERSECT_OUT_SEGMENT, t1, t2, crossPt);
		}
	}


	
	
	
	

	public Vec2 getP0() {
		return p0;
	}

	public Vec2 getP1() {
		return p1;
	}

	public Vec2 getV() {
		return v;
	}
}

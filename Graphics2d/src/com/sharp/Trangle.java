package com.sharp;

public class Trangle {
	private Vec2i pointTop;
	private Vec2i pointMid;
	private Vec2i pointBottom;
	
	public Vec2i getPointTop() {
		return pointTop;
	}

	public Vec2i getPointMid() {
		return pointMid;
	}

	public Vec2i getPointBottom() {
		return pointBottom;
	}

	public Trangle(Vec2i pt0, Vec2i pt1, Vec2i pt2) {
		if ((pt0.getX() == pt1.getX() && pt1.getX() == pt2.getX()) || 
				(pt0.getY() == pt1.getY() && pt1.getY() == pt2.getY())) {
			return;
		}
		
		pointTop = pt0;
		pointMid = pt1;
		pointBottom = pt2;
		
		if (pointTop.getY() > pointMid.getY()) {
			Vec2i.swap(pointTop,  pointMid);
		}
		
		if (pointTop.getY() > pointBottom.getY()) {
			Vec2i.swap(pointTop,  pointBottom);
		}
		
		if (pointMid.getY() > pointBottom.getY()) {
			Vec2i.swap(pointMid,  pointBottom);
		}
		
//		if (p0.getY() > p1.getY()) {
//			if (p0.getY() > p2.getY()) {
//				pointTop = p0;
//				
//				if (p1.getY() > p2.getY()) {
//					pointMid = p1;
//					pointBottom = p2;
//				} else {
//					pointMid = p2;
//					pointBottom = p1;
//				}
//			} else {
//				pointTop = p2;
//				pointMid = p0;
//				pointBottom = p1;
//			}
//		} else {
//			if (p1.getY() > p2.getY()) {
//				pointTop = p1;
//				if (p0.getY() > p2.getY()) {
//					pointMid = p0;
//					pointBottom = p2;
//				} else {
//					pointMid = p2;
//					pointBottom = p0;
//				}
//			} else {
//				pointTop = p2;
//				pointMid = p1;
//				pointBottom = p0;
//			}
//		}
	}
	

}














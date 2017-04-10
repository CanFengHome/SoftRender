package com.sharp;

public class GraphicsTool {

	public static void drawLineHorizontal(int x1, int y1, int x2, int y2, Color3B color, Bitmap frameBuffer) {
		assert y1 == y2;
		int step = 1;
		if (x1 > x2) {
			step = -1;
		}
		
		for (int x = x1; x != x2 + step; x += step) {
			frameBuffer.DrawPixel(x, y1, color);
		}
	}
	
	public static void drawLineVertical(int x1, int y1, int x2, int y2, Color3B color, Bitmap frameBuffer) {
		assert x1 == x2;
		int step = 1;
		if (y1 > y2) {
			step = -1;
		}
		
		for (int y = y1; y != y2 + step; y += step) {
			frameBuffer.DrawPixel(x1, y, color);
		}
	}
	
	public static void drawLineDDA(int x1, int y1, int x2, int y2, Color3B color, Bitmap frameBuffer) {
	    int dy = y2 - y1;
	    int dx = x2 - x1;
	    int maxLen = Math.max(Math.abs(dy), Math.abs(dx));
	    float yStep = (float)(dy)/(float)(maxLen);
	    float y = y1 + 0.5f;
	    
	    for (int i=0; i < maxLen; ++i)
	    {
	    	frameBuffer.DrawPixel((int)Math.floor(x1), (int)Math.floor(y), color);
	        y += yStep;
	    }
	}
	
	public static void drawLineBresenham(Vec2i p0, Vec2i p1, Color3B color, Bitmap frameBuffer) {
		drawLineBresenham(p0.getX(), p0.getY(), p1.getX(), p1.getY(), color, frameBuffer);
	}
	
	public static void drawLineBresenham(int x1, int y1, int x2, int y2, Color3B color, Bitmap frameBuffer) {
		if (x1 == x2) {
			drawLineVertical(x1, y1, x2, y2, color, frameBuffer);
	        return;
	    } else if (y1 == y2) {
	    	drawLineHorizontal(x1, y1, x2, y2, color, frameBuffer);
	        return;
	    }
	    
		int dx = Math.abs(x2 - x1); 
		int dy = Math.abs(y2 - y1); 
		int sx = (x1 < x2) ? 1 : -1; 
		int sy = (y1 < y2) ? 1 : -1; 
		int err = dx - dy; 
		while (true) {
			frameBuffer.DrawPixel(x1, y1, color);
			if (x1 == x2 && y1 == y2) {
				break;
			}
			int e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x1 = x1 + sx;
			}
			if (e2 < dx) {
				err = err + dx;
				y1 = y1 + sy;
			}
		}
	}
	
//	public static void drawTrangle(Trangle trangle, Color3B color, Bitmap frameBuffer) {
//		drawLineBresenham(trangle.getPointTop(), trangle.getPointMid(), color, frameBuffer);
//		drawLineBresenham(trangle.getPointTop(), trangle.getPointBottom(), color, frameBuffer);
//		drawLineBresenham(trangle.getPointMid(), trangle.getPointBottom(), color, frameBuffer);
//	}
//
//	public static void fillTrangle(Trangle trangle, Color3B color, Bitmap frameBuffer) {
//		Vec2i pt0 = trangle.getPointTop();
//		Vec2i pt1 = trangle.getPointMid();
//		Vec2i pt2 = trangle.getPointBottom();
//		
//		if (pt0.getY() == pt1.getY()) {
//			fillTopTrangle(pt0, pt1, pt2, color, frameBuffer);
//		} else if (pt1.getY() == pt2.getY()) {
//			fillBottomTrangle(pt0, pt1, pt2, color, frameBuffer);
//		} else {
//			int new_x = (int)(pt0.getX() + 0.5f + 1.0f *(pt1.getY() - pt0.getY()) * (pt2.getX() - pt0.getX()) / (pt2.getY() - pt0.getY()));
//			Vec2i newPt = new Vec2i(new_x, pt1.getY());
//			fillBottomTrangle(pt0, newPt, pt1, color, frameBuffer);
//			fillTopTrangle(newPt, pt1, pt2, color, frameBuffer);
//		}
//	}
	
	//     p0----p1
	//       ---
	//        p2
	private static void fillTopTrangle(Vec2i pt0, Vec2i pt1, Vec2i pt2, Color3B color, Bitmap frameBuffer) {		
		if (pt0.getY() != pt1.getY()) {
			if (pt0.getY() == pt2.getY()) {
				Vec2i.swap(pt1, pt2);
			} else if (pt1.getY() == pt2.getY()) {
				Vec2i.swap(pt0, pt2);
			} else {
				assert false;
				return;
			}
		}
		
		if (pt1.getX() < pt0.getX()) {
			Vec2i.swap(pt0, pt1);
		} else if (pt1.getX() == pt0.getX()) {
			assert false;
			return;
		}
		
		float dxy_left = (pt2.getX() - pt0.getX()) * 1.0f / (pt2.getY() - pt0.getY());
		float dxy_right = (pt1.getX() - pt2.getX()) * 1.0f / (pt1.getY() - pt2.getY());
		
		float xStart = pt0.getX();
		float xEnd = pt1.getX();
		for (int yIndex = pt0.getY(); yIndex <= pt2.getY(); ++yIndex) {
			drawLineBresenham((int)(xStart + 0.5f), yIndex, (int)(xEnd + 0.5f), yIndex, color, frameBuffer);

			xStart += dxy_left;
			xEnd += dxy_right;
		}
	}
	
	//       p0
	//      ---
	//    p2----p1
	private static void fillBottomTrangle(Vec2i pt0, Vec2i pt1, Vec2i pt2, Color3B color, Bitmap frameBuffer) {		
		if (pt2.getY() != pt1.getY()) {
			if (pt2.getY() == pt0.getY()) {
				Vec2i.swap(pt0, pt1);
			} else if (pt0.getY() == pt1.getY()) {
				Vec2i.swap(pt0, pt2);
			} else {
				assert false;
				return;
			}
		}
		
		if (pt1.getX() < pt2.getX()) {
			Vec2i.swap(pt1, pt2);
		} else if (pt1.getX() == pt2.getX()) {
			assert false;
			return;
		}
		
		float dxy_left = (pt2.getX() - pt0.getX()) * 1.0f / (pt2.getY() - pt0.getY());
		float dxy_right = (pt1.getX() - pt0.getX()) * 1.0f / (pt1.getY() - pt0.getY());
		
		float xStart = pt0.getX();
		float xEnd = pt0.getX();
		
		for (int yIndex = pt0.getY(); yIndex <= pt2.getY(); ++yIndex) {
			drawLineBresenham((int)(xStart + 0.5f), yIndex, (int)(xEnd + 0.5f), yIndex, color, frameBuffer);

			xStart += dxy_left;
			xEnd += dxy_right;
		}
	}
	

}





























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
	
	public static void drawLineBresenham(int x1, int y1, int x2, int y2, Color3B color, Bitmap frameBuffer) {
		if (x1 == x2) {
			drawLineVertical(x1, y1, x2, y2, color, frameBuffer);
	        return;
	    } else if (y1 == y2) {
	    	drawLineHorizontal(x1, y1, x2, y2, color, frameBuffer);
	        return;
	    }
	    
	    float x = x1;
	    float y = y1;
	    float xEnd = x2;
	    float k = (float)(y2-y1) / (float)(x2-x1);
	    if (x1 > x2) {
	        x = x2;
	        y = y2;
	        xEnd = x1;
	        k = (float)(y1-y2) / (float)(x1-x2);
	    }
	    
	    float d = 0.5f-k;
	    for (; x<=xEnd; ++x)
	    {
	    	frameBuffer.DrawPixel((int)Math.floor(x+0.5f), (int)Math.floor(y+0.5f), color);
	        if (d<0) {
	            ++y;
	            d = d+1-k;
	        } else {
	            d = d-k;
	        }
	    }

	}

}

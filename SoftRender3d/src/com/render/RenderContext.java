package com.render;

import com.engine.math.Matrix4f;

public class RenderContext extends Bitmap
{
	public RenderContext(int width, int height)
	{
		super(width, height);
	}

	public void fillTriangle(Vertex v1, Vertex v2, Vertex v3) {
		Matrix4f screenSpaceTransform = 
				new Matrix4f().InitScreenSpaceTransform((float)m_width/2.0f, (float)m_height/2.0f);
		Vertex minYVert = v1.Transform(screenSpaceTransform).PerspectiveDivide();
		Vertex midYVert = v2.Transform(screenSpaceTransform).PerspectiveDivide();
		Vertex maxYVert = v3.Transform(screenSpaceTransform).PerspectiveDivide();
		
		if (maxYVert.getY() < midYVert.getY()) {
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		if (midYVert.getY() < minYVert.getY()) {
			Vertex temp = midYVert;
			midYVert = minYVert;
			minYVert = temp;
		}

		if (maxYVert.getY() < midYVert.getY()) {
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		// 小于0，中间点在左侧
		float area = minYVert.TriangleAreaTimesTwo(maxYVert, midYVert);		
		scanTriangle(minYVert, midYVert, maxYVert, area >= 0);
	}

	private void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness) {
		Edge topToBottom    = new Edge(minYVert, maxYVert);
		Edge topToMiddle    = new Edge(minYVert, midYVert);
		Edge middleToBottom = new Edge(midYVert, maxYVert);

		scanEdges(topToBottom, topToMiddle, handedness); // 上半部分
		scanEdges(topToBottom, middleToBottom, handedness); // 下半部分
	}

	private void scanEdges(Edge a, Edge b, boolean handedness) {
		Edge left = a;
		Edge right = b;
		if (handedness) {
			Edge temp = left;
			left = right;
			right = temp;
		}

		int yStart = b.getYStart();
		int yEnd = b.getYEnd();
		for (int j = yStart; j < yEnd; j++) {
			drawScanLine(left, right, j);
			left.step();
			right.step();
		}
	}

	private void drawScanLine(Edge left, Edge right, int j) {
		int xMin = (int)Math.ceil(left.getX());
		int xMax = (int)Math.ceil(right.getX());

		for(int i = xMin; i < xMax; i++)
		{
			drawPixel(i, j, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
		}
	}
}

















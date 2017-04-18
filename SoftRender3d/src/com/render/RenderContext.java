package com.render;

import com.engine.math.Matrix4f;
import com.engine.math.Vector4f;

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
		Gradients gradients = new Gradients(minYVert, midYVert, maxYVert);
		Edge topToBottom    = new Edge(gradients, minYVert, maxYVert, 0);
		Edge topToMiddle    = new Edge(gradients, minYVert, midYVert, 0);
		Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);

		scanEdges(gradients, topToBottom, topToMiddle, handedness); // 上半部分
		scanEdges(gradients, topToBottom, middleToBottom, handedness); // 下半部分
	}

	private void scanEdges(Gradients gradients, Edge a, Edge b, boolean handedness) {
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
			drawScanLine(gradients, left, right, j);
			left.step();
			right.step();
		}
	}

	private void drawScanLine(Gradients gradients, Edge left, Edge right, int j) {
		int xMin = (int)Math.ceil(left.getX());
		int xMax = (int)Math.ceil(right.getX());
		float xPrestep = xMin - left.getX();
		
		Vector4f color = left.getColor().Add(gradients.GetColorXStep().Mul(xPrestep));

		for(int i = xMin; i < xMax; i++)
		{
			byte r = (byte)(color.GetX() * 255.0f + 0.5f);
			byte g = (byte)(color.GetY() * 255.0f + 0.5f);
			byte b = (byte)(color.GetZ() * 255.0f + 0.5f);

			drawPixel(i, j, (byte)0xFF, b, g, r);
			color = color.Add(gradients.GetColorXStep());
		}
	}
}

















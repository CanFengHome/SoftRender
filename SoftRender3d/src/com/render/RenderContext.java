package com.render;

import com.engine.math.Matrix4f;
import com.engine.math.Vector4f;

public class RenderContext extends Bitmap
{
	public RenderContext(int width, int height)
	{
		super(width, height);
	}
	
	public void drawMesh(Mesh mesh, Matrix4f transform, Bitmap texture)
	{
		for(int i = 0; i < mesh.GetNumIndices(); i += 3)
		{
			fillTriangle(
					mesh.GetVertex(mesh.GetIndex(i)).Transform(transform),
					mesh.GetVertex(mesh.GetIndex(i + 1)).Transform(transform),
					mesh.GetVertex(mesh.GetIndex(i + 2)).Transform(transform),
					texture);
		}
	}

	public void fillTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture) {
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
		scanTriangle(minYVert, midYVert, maxYVert, area >= 0, texture);
	}

	private void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness, Bitmap texture) {
		Gradients gradients = new Gradients(minYVert, midYVert, maxYVert);
		Edge topToBottom    = new Edge(gradients, minYVert, maxYVert, 0);
		Edge topToMiddle    = new Edge(gradients, minYVert, midYVert, 0);
		Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);

		scanEdges(topToBottom, topToMiddle, handedness, texture); // 上半部分
		scanEdges(topToBottom, middleToBottom, handedness, texture); // 下半部分
	}

	private void scanEdges(Edge a, Edge b, boolean handedness, Bitmap texture) {
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
			drawScanLine(left, right, j, texture);
			left.step();
			right.step();
		}
	}

	private void drawScanLine(Edge left, Edge right, int j, Bitmap texture) {
		int xMin = (int)Math.ceil(left.getX());
		int xMax = (int)Math.ceil(right.getX());
		float xPrestep = xMin - left.getX();
		
		float xDist = right.getX() - left.getX();
		float texCoordXXStep = (right.GetTexCoordX() - left.GetTexCoordX())/xDist;
		float texCoordYXStep = (right.GetTexCoordY() - left.GetTexCoordY())/xDist;
		float oneOverZXStep = (right.GetOneOverZ() - left.GetOneOverZ())/xDist;

		float texCoordX = left.GetTexCoordX() + texCoordXXStep * xPrestep;
		float texCoordY = left.GetTexCoordY() + texCoordYXStep * xPrestep;
		float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;

		for(int i = xMin; i < xMax; i++)
		{
			float z = 1.0f/oneOverZ;
			int srcX = (int)((texCoordX * z) * (float)(texture.getWidth() - 1) + 0.5f);
			int srcY = (int)((texCoordY * z) * (float)(texture.getHeight() - 1) + 0.5f);

			copyPixel(i, j, srcX, srcY, texture);
			oneOverZ += oneOverZXStep;
			texCoordX += texCoordXXStep;
			texCoordY += texCoordYXStep;
		}
	}
}

















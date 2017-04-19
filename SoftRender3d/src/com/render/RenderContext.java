package com.render;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.engine.math.Matrix4f;
import com.engine.math.Vector4f;

public class RenderContext extends Bitmap
{
	private float[] m_zBuffer;
	
	public RenderContext(int width, int height)
	{
		super(width, height);
		m_zBuffer = new float[width * height];
	}
	
	public void ClearDepthBuffer()
	{
		for(int i = 0; i < m_zBuffer.length; i++)
		{
			m_zBuffer[i] = Float.MAX_VALUE;
		}
	}
	
	public void drawTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture)
	{
		boolean v1Inside = v1.IsInsideViewFrustum();
		boolean v2Inside = v2.IsInsideViewFrustum();
		boolean v3Inside = v3.IsInsideViewFrustum();

		if(v1Inside && v2Inside && v3Inside)
		{
			fillTriangle(v1, v2, v3, texture);
			return;
		}

		if(!v1Inside && !v2Inside && !v3Inside)
		{
			return;
		}

		List<Vertex> vertices = new ArrayList<>();
		List<Vertex> auxillaryList = new ArrayList<>(); // 辅助
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);

		if(ClipPolygonAxis(vertices, auxillaryList, 0) &&
				ClipPolygonAxis(vertices, auxillaryList, 1) &&
				ClipPolygonAxis(vertices, auxillaryList, 2))
		{
			Vertex initialVertex = vertices.get(0);

			for(int i = 1; i < vertices.size() - 1; i++)
			{
				fillTriangle(initialVertex, vertices.get(i), vertices.get(i + 1), texture);
			}
		}
	}

	private boolean ClipPolygonAxis(List<Vertex> vertices, List<Vertex> auxillaryList,
			int componentIndex)
	{
		ClipPolygonComponent(vertices, componentIndex, 1.0f, auxillaryList);
		vertices.clear();

		if(auxillaryList.isEmpty())
		{
			return false;
		}

		ClipPolygonComponent(auxillaryList, componentIndex, -1.0f, vertices);
		auxillaryList.clear();

		return !vertices.isEmpty();
	}

	private void ClipPolygonComponent(List<Vertex> vertices, int componentIndex, 
			float componentFactor, List<Vertex> result)
	{
		Vertex previousVertex = vertices.get(vertices.size() - 1);
		float previousComponent = previousVertex.get(componentIndex) * componentFactor;
		boolean previousInside = previousComponent <= previousVertex.getPosition().GetW();

		Iterator<Vertex> it = vertices.iterator();
		while(it.hasNext())
		{
			Vertex currentVertex = it.next();
			float currentComponent = currentVertex.get(componentIndex) * componentFactor;
			boolean currentInside = currentComponent <= currentVertex.getPosition().GetW();

			if(currentInside ^ previousInside)
			{
				float lerpAmt = (previousVertex.getPosition().GetW() - previousComponent) /
					((previousVertex.getPosition().GetW() - previousComponent) - 
					 (currentVertex.getPosition().GetW() - currentComponent));

				result.add(previousVertex.Lerp(currentVertex, lerpAmt));
			}

			if(currentInside)
			{
				result.add(currentVertex);
			}

			previousVertex = currentVertex;
			previousComponent = currentComponent;
			previousInside = currentInside;
		}
	}
	
	public void fillTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture) {
		Matrix4f screenSpaceTransform = 
				new Matrix4f().InitScreenSpaceTransform((float)m_width/2.0f, (float)m_height/2.0f);
		Vertex minYVert = v1.Transform(screenSpaceTransform).PerspectiveDivide();
		Vertex midYVert = v2.Transform(screenSpaceTransform).PerspectiveDivide();
		Vertex maxYVert = v3.Transform(screenSpaceTransform).PerspectiveDivide();
		
		// 只绘制顺时针的面
		if(minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0)
		{
			return;
		}
		
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
		float depthXStep = (right.GetDepth() - left.GetDepth())/xDist;

		float texCoordX = left.GetTexCoordX() + texCoordXXStep * xPrestep;
		float texCoordY = left.GetTexCoordY() + texCoordYXStep * xPrestep;
		float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;
		float depth = left.GetDepth() + depthXStep * xPrestep;
		
		for(int i = xMin; i < xMax; i++)
		{
			int index = i + j * getWidth();
			if(depth < m_zBuffer[index])
			{
				m_zBuffer[index] = depth;
				float z = 1.0f/oneOverZ;
				int srcX = (int)((texCoordX * z) * (float)(texture.getWidth() - 1) + 0.5f);
				int srcY = (int)((texCoordY * z) * (float)(texture.getHeight() - 1) + 0.5f);

				copyPixel(i, j, srcX, srcY, texture);
			}

			oneOverZ += oneOverZXStep;
			texCoordX += texCoordXXStep;
			texCoordY += texCoordYXStep;
			depth += depthXStep;
		}
	}
}

















package com.render;

import com.engine.math.Vector4f;

public class Edge {
	private float m_x;
	private float m_xStep;
	private int m_yStart;
	private int m_yEnd;
	
	private Vector4f m_color;
	private Vector4f m_colorStep;

	public float getX() {
		return m_x;
	}

	public int getYStart() {
		return m_yStart;
	}

	public int getYEnd() {
		return m_yEnd;
	}
	
	public Vector4f getColor() { return m_color; }

	public Edge(Gradients gradients, Vertex minYVert, Vertex maxYVert, int minYVertIndex) {
		m_yStart = (int) Math.ceil(minYVert.getY());
		m_yEnd = (int) Math.ceil(maxYVert.getY());

		float yDist = maxYVert.getY() - minYVert.getY();
		float xDist = maxYVert.getX() - minYVert.getX();

		float yPrestep = m_yStart - minYVert.getY();
		m_xStep = (float) xDist / (float) yDist;
		m_x = minYVert.getX() + yPrestep * m_xStep;
		
		float xPrestep = m_x - minYVert.getX();
		m_color = gradients.GetColor(minYVertIndex).Add(
				gradients.GetColorYStep().Mul(yPrestep)).Add(
				gradients.GetColorXStep().Mul(xPrestep));
		m_colorStep = gradients.GetColorYStep().Add(gradients.GetColorXStep().Mul(m_xStep));
	}

	public void step() {
		m_x += m_xStep;
		m_color = m_color.Add(m_colorStep);
	}
}











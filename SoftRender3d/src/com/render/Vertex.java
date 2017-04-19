package com.render;

import com.engine.math.Matrix4f;
import com.engine.math.Vector4f;

public class Vertex {
	private Vector4f pos;
	private Vector4f m_texCoords;

	public float getX() {
		return pos.GetX();
	}

	public float getY() {
		return pos.GetY();
	}
	
	public Vector4f getPosition() { return pos; }
	public Vector4f GetTexCoords() { return m_texCoords; }

	public Vertex(float x, float y, float z) {
		this.pos = new Vector4f(x, y, z, 1.0f);
	}
	
	public Vertex(Vector4f pos, Vector4f texCoords)
	{
		this.pos = pos;
		m_texCoords = texCoords;
	}

	public float TriangleAreaTimesTwo(Vertex b, Vertex c) {
		float x1 = b.getX() - pos.GetX();
		float y1 = b.getY() - pos.GetY();

		float x2 = c.getX() - pos.GetX();
		float y2 = c.getY() - pos.GetY();

		return (x1 * y2 - x2 * y1);
	}

	public Vertex Transform(Matrix4f transform) {
		return new Vertex(transform.Transform(pos), m_texCoords);
	}

	public Vertex PerspectiveDivide() {
		return new Vertex(new Vector4f(pos.GetX()/pos.GetW(), pos.GetY()/pos.GetW(), 
				pos.GetZ()/pos.GetW(), pos.GetW()), m_texCoords);
	}
	
	public float get(int index)
	{
		switch(index)
		{
			case 0:
				return pos.GetX();
			case 1:
				return pos.GetY();
			case 2:
				return pos.GetZ();
			case 3:
				return pos.GetW();
			default:
				throw new IndexOutOfBoundsException();
		}
	}
	
	public Vertex Lerp(Vertex other, float lerpAmt)
	{
		return new Vertex(
				pos.Lerp(other.getPosition(), lerpAmt),
				m_texCoords.Lerp(other.GetTexCoords(), lerpAmt));
	}

	public boolean IsInsideViewFrustum()
	{
		return 
			Math.abs(pos.GetX()) <= Math.abs(pos.GetW()) &&
			Math.abs(pos.GetY()) <= Math.abs(pos.GetW()) &&
			Math.abs(pos.GetZ()) <= Math.abs(pos.GetW());
	}

}












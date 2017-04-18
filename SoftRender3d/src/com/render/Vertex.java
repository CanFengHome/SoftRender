package com.render;

import com.engine.math.Matrix4f;
import com.engine.math.Vector4f;

public class Vertex {
	private Vector4f pos;

	public float getX() {
		return pos.GetX();
	}

	public float getY() {
		return pos.GetY();
	}

	public Vertex(float x, float y, float z) {
		pos = new Vector4f(x, y, z, 1);
	}

	public Vertex(Vector4f pos) {
		this.pos = pos;
	}

	public float TriangleAreaTimesTwo(Vertex b, Vertex c) {
		float x1 = b.getX() - pos.GetX();
		float y1 = b.getY() - pos.GetY();

		float x2 = c.getX() - pos.GetX();
		float y2 = c.getY() - pos.GetY();

		return (x1 * y2 - x2 * y1);
	}

	public Vertex Transform(Matrix4f transform) {
		return new Vertex(transform.Transform(pos));
	}

	public Vertex PerspectiveDivide() {
		return new Vertex(new Vector4f(pos.GetX()/pos.GetW(), pos.GetY()/pos.GetW(), 
				pos.GetZ()/pos.GetW(), pos.GetW()));
	}
}
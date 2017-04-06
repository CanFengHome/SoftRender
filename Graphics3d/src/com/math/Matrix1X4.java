package com.math;

public class Matrix1X4 {
	// 00 01 02 03
	private float m[] = new float[4];
	
	public void copy(Matrix1X4 matrix) {
		for (int i=0; i<4; ++i) {
			m[i] = matrix.m[i];
		}
	}
}

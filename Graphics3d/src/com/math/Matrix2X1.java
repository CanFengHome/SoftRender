package com.math;

public class Matrix2X1 {
	private float m[][] = new float[2][1];
	public Matrix2X1() {
		
	}
	
	public Matrix2X1(float m00, float m10) {
		m[0][0] = m00;
		m[1][0] = m10;
	}
	
	public void copy(Matrix2X1 rhs) {
		m[0][0] = rhs.m[0][0];
		m[1][0] = rhs.m[1][0];
	}
	
	public float getM00() {
		return m[0][0];
	}
	
	public float getM10() {
		return m[1][0];
	}
	
	public void setM00(float m00) {
		m[0][0] = m00;
	}
	
	public void setM10(float m10) {
		m[1][0] = m10;
	}
	
	public void print() {
		System.out.println(m[0][0] + " " + m[1][0]);
	}
}















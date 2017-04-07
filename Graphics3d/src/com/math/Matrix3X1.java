package com.math;

public class Matrix3X1 {
	// 00
	// 10
	// 20
	private float m[][] = new float[3][1];
	public Matrix3X1() {
		
	}
	
	public Matrix3X1(float m00, float m10, float m20) {
		m[0][0] = m00;
		m[1][0] = m10;
		m[2][0] = m20;
	}
	
	public void copy(Matrix3X1 rhs) {
		m[0][0] = rhs.m[0][0];
		m[1][0] = rhs.m[1][0];
		m[2][0] = rhs.m[2][0];
	}
	
	public float getM00() {
		return m[0][0];
	}
	
	public float getM10() {
		return m[1][0];
	}
	
	public float getM20() {
		return m[2][0];
	}
	
	public void setM00(float m00) {
		m[0][0] = m00;
	}
	
	public void setM10(float m10) {
		m[1][0] = m10;
	}
	
	public void setM20(float m20) {
		m[2][0] = m20;
	}
	
	public void print() {
		System.out.println(m[0][0] + " " + m[1][0] + " " + m[2][0]);
	}
}















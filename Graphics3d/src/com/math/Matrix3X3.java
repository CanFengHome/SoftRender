package com.math;

public class Matrix3X3 {
	// 00 01 02
	// 10 11 12
	// 20 21 22
	private float m[][] = new float[3][3];
	
	public void identity() {
		m = new float[][] {
			{1,0,0},
			{0,1,0},
			{0,0,1}
		};
	}
	
	public void copy(Matrix3X3 matrix) {
		for (int row=0; row<3; ++row) {
			for (int col=0; col<3; ++col) {
				m[row][col] = matrix.m[row][col];
			}
		}
	}
	
	public void transpose() {
		float arr[][] = m;
		m = new float[3][3];
		
		for (int row=0; row<3; ++row) {
			for (int col=0; col<3; ++col) {
				m[row][col] = arr[col][row];
			}
		}
	}
	
	public void swapColumn(Matrix3X3 mat, int column) {
		if (column >= 3 || column < 0) {
			return;
		}
		
		m[0][column] = mat.m[0][column];
		m[1][column] = mat.m[1][column];
		m[2][column] = mat.m[2][column];
	}
	
	public void print() {
		for (int row=0; row<3; ++row) {
			for (int col=0; col<3; ++col) {
				System.out.print(m[row][col] + " ");
			}
			System.out.println();
		}
	}
	
//	public static void main(String[] args) {
//		Matrix3X3 mat = new Matrix3X3();
//		mat.identity();
//		mat.m[1][0] = 5;
//		mat.m[2][1] = 4;
//		mat.print();
//		System.out.println("-----------");
//		mat.transpose();
//		mat.print();
//	}
}


















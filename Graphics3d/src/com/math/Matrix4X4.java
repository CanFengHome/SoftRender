package com.math;

public class Matrix4X4 {
	// 00 01 02 03
	// 10 11 12 13
	// 20 21 22 23
	// 30 31 32 33
	private float m[][] = new float[4][4];
	
	public void identity() {
		m = new float[][] {
			{1,0,0,0},
			{0,1,0,0},
			{0,0,1,0},
			{0,0,0,1}
		};
	}
	
	public void copy(Matrix4X4 matrix) {
		for (int row=0; row<4; ++row) {
			for (int col=0; col<4; ++col) {
				m[row][col] = matrix.m[row][col];
			}
		}
	}
	
	public void transpose() {
		float arr[][] = m;
		m = new float[4][4];
		
		for (int row=0; row<4; ++row) {
			for (int col=0; col<4; ++col) {
				m[row][col] = arr[col][row];
			}
		}
	}
	
	public void print() {
		for (int row=0; row<4; ++row) {
			for (int col=0; col<4; ++col) {
				System.out.print(m[row][col] + " ");
			}
			System.out.println();
		}
	}
	
	public void swapColumn(Matrix4X4 mat, int column) {
		if (column >= 4 || column < 0) {
			return;
		}
		
		m[0][column] = mat.m[0][column];
		m[1][column] = mat.m[1][column];
		m[2][column] = mat.m[2][column];
		m[3][column] = mat.m[3][column];
	}
	
//	public static void main(String[] args) {
//		Matrix4X4 mat = new Matrix4X4();
//		mat.identity();
//		mat.m[1][0] = 5;
//		mat.m[2][1] = 4;
//		mat.m[3][2] = 6;
//		mat.print();
//		System.out.println("-----------");
//		mat.transpose();
//		mat.print();
//	}
}























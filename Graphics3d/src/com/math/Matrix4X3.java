package com.math;

public class Matrix4X3 {
	// 00 01 02
	// 10 11 12
	// 20 21 22
	// 30 31 32
	private float m[][] = new float[4][3];
	
	public void identity() {
		m = new float[][] {
			{1,0,0},
			{0,1,0},
			{0,0,1},
			{0,0,0}
		};
	}
	
	public void copy(Matrix4X3 matrix) {
		for (int row=0; row<4; ++row) {
			for (int col=0; col<3; ++col) {
				m[row][col] = matrix.m[row][col];
			}
		}
	}
	
	public void swapColumn(Matrix4X3 mat, int column) {
		if (column >= 3 || column < 0) {
			return;
		}
		
		m[0][column] = mat.m[0][column];
		m[1][column] = mat.m[1][column];
		m[2][column] = mat.m[2][column];
		m[3][column] = mat.m[3][column];
	}
	
	public void print() {
		for (int row=0; row<4; ++row) {
			for (int col=0; col<3; ++col) {
				System.out.print(m[row][col] + " ");
			}
			System.out.println();
		}
	}
	
//	public static void main(String[] args) {
//		Matrix4X3 mat1 = new Matrix4X3();
//		mat1.identity();
//		mat1.m[1][0] = 5;
//		mat1.m[2][1] = 4;
//		mat1.print();
//		System.out.println("-----------");
//		Matrix4X3 mat2 = new Matrix4X3();
//		mat2.print();
//		System.out.println("-----------");
//		mat2.swapColumn(mat1, 2);
//		mat2.swapColumn(mat1, 1);
//		mat2.swapColumn(mat1, 0);
//		mat2.print();
//	}
}

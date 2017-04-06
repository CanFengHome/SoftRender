package com.math;

public class Matrix2X2 {
	// 00 01
	// 10 11
	private float m[][] = new float[2][2];
	
	public Matrix2X2() {
		
	}
	
	public Matrix2X2(float m00, float m01, 
					 float m10, float m11) {
		m[0][0] = m00; m[0][1] = m01;
		m[1][0] = m10; m[1][1] = m11;
	}
	
	public void identity() {
		m = new float[][] {
			{1,0},
			{0,1}
		};
	}
	
	public void copy(Matrix2X2 matrix) {
		for (int row=0; row<2; ++row) {
			for (int col=0; col<2; ++col) {
				m[row][col] = matrix.m[row][col];
			}
		}
	}
	
	public float getDet() {
		return m[0][0] * m[1][1] - m[0][1] * m[1][0];
	}
	
	public static Matrix2X2 add(Matrix2X2 lhs, Matrix2X2 rhs) {
		Matrix2X2 mat = new Matrix2X2();
		for (int row=0; row<2; ++row) {
			for (int col=0; col<2; ++col) {
				mat.m[row][col] = lhs.m[row][col] + rhs.m[row][col];
			}
		}
		return mat;
	}
	
	public static Matrix2X2 multiply(Matrix2X2 lhs, Matrix2X2 rhs) {
		Matrix2X2 mat = new Matrix2X2();
		mat.m[0][0] = lhs.m[0][0] * rhs.m[0][0] + lhs.m[0][1] * rhs.m[1][0];
		mat.m[0][1] = lhs.m[0][0] * rhs.m[0][1] + lhs.m[0][1] * rhs.m[1][1];

		mat.m[1][0] = lhs.m[1][0] * rhs.m[0][0] + lhs.m[1][1] * rhs.m[1][0];
		mat.m[1][1] = lhs.m[1][0] * rhs.m[0][1] + lhs.m[1][1] * rhs.m[1][1];
		return mat;
	}
	
	public static Matrix2X1 multiply(Matrix2X2 lhs, Matrix2X1 rhs) {
		Matrix2X1 mat = new Matrix2X1(lhs.m[0][0] * rhs.getM00() + lhs.m[0][1] * rhs.getM10(),
				lhs.m[1][0] * rhs.getM00() + lhs.m[1][1] * rhs.getM10());
		return mat;
	}
	
	public static boolean inverse(Matrix2X2 mat, Matrix2X2 returnMat) {
		float det = mat.getDet();
		
		if (Math.abs(det) < MathUtil.EPSILON5) {
			return false;
		}
		
		float det_inv = 1.0f / det;
		returnMat.m[0][0] = mat.m[1][1] * det_inv;
		returnMat.m[0][1] = -mat.m[0][1] * det_inv;
		returnMat.m[1][0] = -mat.m[1][0] * det_inv;
		returnMat.m[1][1] = mat.m[0][0] * det_inv;
		
		return true;
	}
	
	public void transpose() {
		float arr[][] = m;
		m = new float[2][2];
		
		for (int row=0; row<2; ++row) {
			for (int col=0; col<2; ++col) {
				m[row][col] = arr[col][row];
			}
		}
	}
	
	public void swapColumn(Matrix2X2 mat, int column) {
		if (column >= 2 || column < 0) {
			return;
		}
		
		m[0][column] = mat.m[0][column];
		m[1][column] = mat.m[1][column];
	}
	
	public void print() {
		for (int row=0; row<2; ++row) {
			for (int col=0; col<2; ++col) {
				System.out.print(m[row][col] + " ");
			}
			System.out.println();
		}
	}
	
	public static boolean solveSystem(Matrix2X2 A, Matrix2X1 X, Matrix2X1 B) {
		float det = A.getDet();
		if (Math.abs(det) < MathUtil.EPSILON5) {
			return false;
		}
		
		Matrix2X2 mat = new Matrix2X2();
		if (Matrix2X2.inverse(A, mat) == false)
		{
			return false;
		}
		
		Matrix2X1 solve = multiply(mat, B);
		X.copy(solve);
		return true;
	}
	
//	public static void main(String[] args) {
////		Matrix2X2 mat = new Matrix2X2();
////		mat.identity();
////		mat.m[1][0] = 5;
////		mat.m[0][1] = 4;
////		mat.print();
////		System.out.println("-----------");
////		mat.transpose();
////		mat.print();
//		
////		//-------------inverse
////		Matrix2X2 mat = new Matrix2X2(1, 3, 2, 7);
////		mat.print();
////		
////		Matrix2X2 resultMat = new Matrix2X2();
////		if (Matrix2X2.inverse(mat, resultMat)) {
////			resultMat.print();
////		} else {
////			System.out.println("none inverse");
////		}
//		
//		//---------------solv system
//		Matrix2X2 A = new Matrix2X2(3, 1, 1, 1);
//		Matrix2X1 X = new Matrix2X1();
//		Matrix2X1 B = new Matrix2X1(4, 2);
//		
//		if (Matrix2X2.solveSystem(A, X, B)) {
//			X.print();
//		} else {
//			System.out.println("solv fail...");
//		}
//	}
}














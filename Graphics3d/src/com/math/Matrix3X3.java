package com.math;

public class Matrix3X3 {
	// 00 01 02
	// 10 11 12
	// 20 21 22
	private float m[][] = new float[3][3];
	
	public Matrix3X3() {
		
	}
	
	public Matrix3X3(float m00, float m01, float m02, 
					 float m10, float m11, float m12,
					 float m20, float m21, float m22) {
		m[0][0] = m00; m[0][1] = m01; m[0][2] = m02;
		m[1][0] = m10; m[1][1] = m11; m[1][2] = m12;
		m[2][0] = m20; m[2][1] = m21; m[2][2] = m22;
	}
	
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
	
	public static Vec3 vec3MultiplyMat3X3(Vec3 va, Matrix3X3 mb) {
		float vaArr[] = new float[]{ va.getX(), va.getY(), va.getZ()};
		float resultArr[] = new float[3];
		for (int col=0; col<3; ++col) {
			float sum = 0;
			for (int row=0; row<3; ++row) {
				sum += vaArr[row] * mb.m[row][col];
			}
			resultArr[col] = sum;
		}
		
		return new Vec3(resultArr[0], resultArr[1], resultArr[2]);
	}
	
	public float getDet() {
		// computes the determinate of a 3x3 matrix using co-factor
		// expansion

		return(m[0][0]*(m[1][1]*m[2][2] - m[2][1]*m[1][2]) - 
		       m[0][1]*(m[1][0]*m[2][2] - m[2][0]*m[1][2]) + 
		       m[0][2]*(m[1][0]*m[2][1] - m[2][0]*m[1][1]) );
	}
	
	public static boolean inverse(Matrix3X3 mat, Matrix3X3 returnMat) {
		float det = mat.getDet();
		
		if (Math.abs(det) < MathUtil.EPSILON5) {
			return false;
		}
		
		float det_inv = 1.0f / det;
		// compute inverse using m-1 = adjoint(m)/det(m)
		returnMat.m[0][0] =  det_inv*(mat.m[1][1]*mat.m[2][2] - mat.m[2][1]*mat.m[1][2]);
		returnMat.m[1][0] = -det_inv*(mat.m[1][0]*mat.m[2][2] - mat.m[2][0]*mat.m[1][2]);
		returnMat.m[2][0] =  det_inv*(mat.m[1][0]*mat.m[2][1] - mat.m[2][0]*mat.m[1][1]);

		returnMat.m[0][1] = -det_inv*(mat.m[0][1]*mat.m[2][2] - mat.m[2][1]*mat.m[0][2]);
		returnMat.m[1][1] =  det_inv*(mat.m[0][0]*mat.m[2][2] - mat.m[2][0]*mat.m[0][2]);
		returnMat.m[2][1] = -det_inv*(mat.m[0][0]*mat.m[2][1] - mat.m[2][0]*mat.m[0][1]);

		returnMat.m[0][2] =  det_inv*(mat.m[0][1]*mat.m[1][2] - mat.m[1][1]*mat.m[0][2]);
		returnMat.m[1][2] = -det_inv*(mat.m[0][0]*mat.m[1][2] - mat.m[1][0]*mat.m[0][2]);
		returnMat.m[2][2] =  det_inv*(mat.m[0][0]*mat.m[1][1] - mat.m[1][0]*mat.m[0][1]);
		
		return true;
	}
	
	public static Matrix3X1 multiply(Matrix3X3 lhs, Matrix3X1 rhs) {
		Matrix3X1 mat = new Matrix3X1(lhs.m[0][0] * rhs.getM00() + lhs.m[0][1] * rhs.getM10() + lhs.m[0][2] * rhs.getM20(),
				lhs.m[1][0] * rhs.getM00() + lhs.m[1][1] * rhs.getM10() + lhs.m[1][2] * rhs.getM20(), 
				lhs.m[2][0] * rhs.getM00() + lhs.m[2][1] * rhs.getM10() + lhs.m[2][2] * rhs.getM20());
		return mat;
	}
	
	public static boolean solveSystem(Matrix3X3 A, Matrix3X1 X, Matrix3X1 B) {
		float det = A.getDet();
		if (Math.abs(det) < MathUtil.EPSILON5) {
			return false;
		}
		
		Matrix3X3 mat = new Matrix3X3();
		if (Matrix3X3.inverse(A, mat) == false)
		{
			return false;
		}
		
		Matrix3X1 solve = multiply(mat, B);
		X.copy(solve);
		return true;
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
////		Matrix3X3 mat = new Matrix3X3();
////		mat.identity();
////		mat.m[1][0] = 5;
////		mat.m[2][1] = 4;
////		mat.print();
////		System.out.println("-----------");
////		mat.transpose();
////		mat.print();
//
////		// ---------inverse
////		Matrix3X3 mat = new Matrix3X3(1, 3, 0, 5, 2, 1, 4, 0, 1);
////		Matrix3X3 resultMat = new Matrix3X3();
////		Matrix3X3.inverse(mat, resultMat);
////		resultMat.print();
//		
//		//----------------------solve
//		Matrix3X3 A = new Matrix3X3(1, 3, 0, 5, 2, 1, 4, 0, 1);
//		Matrix3X1 X = new Matrix3X1();
//		Matrix3X1 B = new Matrix3X1(0, 0, 1);
//		
//		if (Matrix3X3.solveSystem(A, X, B)) {
//			X.print();
//		} else {
//			System.out.println("solv fail...");
//		}
//	}
}


















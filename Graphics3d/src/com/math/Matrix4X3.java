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
	
	// 4D homogenous vector, thus the function assumes that
	// w=1 to carry out the multiply, also the function
	// does not carry out the last column multiply since
	// we are assuming w=1, there is no point
	public static Vec3 vec3MultiplyMat4X3(Vec3 va, Matrix4X3 mb) {
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
	
	// this function multiplies a VECTOR4D against a 
	// 4x3 matrix - ma*mb and stores the result in mprod
	// the function assumes that the last column of
	// mb is [0 0 0 1]t , thus w just gets replicated
	// from the vector [x y z w]
	public static Vec4 vec4MultiplyMat4X3(Vec4 va, Matrix4X3 mb) {
		float vaArr[] = new float[] { va.getX(), va.getY(), va.getZ(), va.getW() };
		float resultArr[] = new float[4];
		for (int col = 0; col < 4; col++) {
			float sum = 0; // used to hold result
			for (int row = 0; row < 4; row++) {
				// add in next product pair
				sum += vaArr[row] * mb.m[row][col];
			}
			resultArr[col] = sum;
		}
		return new Vec4(resultArr[0], resultArr[1], resultArr[2], va.getW());
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

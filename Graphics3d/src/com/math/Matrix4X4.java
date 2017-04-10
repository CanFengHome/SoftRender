package com.math;

public class Matrix4X4 {
	// 00 01 02 03
	// 10 11 12 13
	// 20 21 22 23
	// 30 31 32 33
	private float m[][] = new float[4][4];
	
	public Matrix4X4() {
		
	}
	
	public Matrix4X4(float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33) {
		m[0][0] = m00; m[0][1] = m01; m[0][2] = m02; m[0][3] = m03;
		m[1][0] = m10; m[1][1] = m11; m[1][2] = m12; m[1][3] = m13;
		m[2][0] = m20; m[2][1] = m21; m[2][2] = m22; m[0][3] = m23;
		m[3][0] = m30; m[3][1] = m31; m[3][2] = m32; m[3][3] = m33;
	}
	
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
	
	public static Matrix4X4 add(Matrix4X4 lhs, Matrix4X4 rhs) {
		Matrix4X4 resultMat = new Matrix4X4();
		for (int row=0; row<4; row++) {
	     for (int col=0; col<4; col++) {
	    	 resultMat.m[row][col] = lhs.m[row][col] + rhs.m[row][col];
	        } 
	    }
		return resultMat;
	}
	
	public static Matrix4X4 multiplyMatrix(Matrix4X4 lhs, Matrix4X4 rhs) {
		Matrix4X4 resultMat = new Matrix4X4();
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				float sum = 0; // used to hold result
				for (int index = 0; index < 4; index++) {
					// add in next product pair
					sum += (lhs.m[row][index] * rhs.m[index][col]);
				} 
				resultMat.m[row][col] = sum;
			}
		}
		return resultMat;
	}
	
	// the function assumes that the vector refers to a 
	// 4D homogenous vector, thus the function assumes that
	// w=1 to carry out the multiply, also the function
	// does not carry out the last column multiply since
	// we are assuming w=1, there is no point
	public static Vec3 vec3MultiplyMat4X4(Vec3 va, Matrix4X4 mb) {
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
	// 4x4 matrix - ma*mb and stores the result in mprod
	// the function makes no assumptions
	public static Vec4 vec4MultiplyMat4X4(Vec4 va, Matrix4X4 mb) {
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
		return new Vec4(resultArr[0], resultArr[1], resultArr[2], resultArr[3]);
	}
	
	public float getDet() {
		// computes the determinate of a 3x3 matrix using co-factor
		// expansion

		return(m[0][0]*(m[1][1]*m[2][2] - m[2][1]*m[1][2]) - 
		       m[0][1]*(m[1][0]*m[2][2] - m[2][0]*m[1][2]) + 
		       m[0][2]*(m[1][0]*m[2][1] - m[2][0]*m[1][1]) );
	}
	
	// TODO:: 有疑问
	public static boolean inverse(Matrix4X4 mat, Matrix4X4 returnMat) {
		float det = mat.getDet();
		
		if (Math.abs(det) < MathUtil.EPSILON5) {
			return false;
		}
		
		float det_inv = 1.0f / det;
		// compute inverse using m-1 = adjoint(m)/det(m)
		returnMat.m[0][0] =  det_inv*(mat.m[1][1]*mat.m[2][2] - mat.m[2][1]*mat.m[1][2]);
		returnMat.m[1][0] = -det_inv*(mat.m[1][0]*mat.m[2][2] - mat.m[2][0]*mat.m[1][2]);
		returnMat.m[2][0] =  det_inv*(mat.m[1][0]*mat.m[2][1] - mat.m[2][0]*mat.m[1][1]);
		returnMat.m[3][0] = -(mat.m[3][0] * mat.m[0][0] + mat.m[3][1] * mat.m[1][0] + mat.m[3][2] * mat.m[2][0]);

		returnMat.m[0][1] = -det_inv*(mat.m[0][1]*mat.m[2][2] - mat.m[2][1]*mat.m[0][2]);
		returnMat.m[1][1] =  det_inv*(mat.m[0][0]*mat.m[2][2] - mat.m[2][0]*mat.m[0][2]);
		returnMat.m[2][1] = -det_inv*(mat.m[0][0]*mat.m[2][1] - mat.m[2][0]*mat.m[0][1]);
		returnMat.m[3][1] = -(mat.m[3][0] * mat.m[0][1] + mat.m[3][1] * mat.m[1][1] + mat.m[3][2] * mat.m[2][1]);

		returnMat.m[0][2] =  det_inv*(mat.m[0][1]*mat.m[1][2] - mat.m[1][1]*mat.m[0][2]);
		returnMat.m[1][2] = -det_inv*(mat.m[0][0]*mat.m[1][2] - mat.m[1][0]*mat.m[0][2]);
		returnMat.m[2][2] =  det_inv*(mat.m[0][0]*mat.m[1][1] - mat.m[1][0]*mat.m[0][1]);
		returnMat.m[3][2] = -(mat.m[3][0] * mat.m[0][2] + mat.m[3][1] * mat.m[1][2] + mat.m[3][2] * mat.m[2][2]);
		
		returnMat.m[0][3] = 0.0f;
		returnMat.m[1][3] = 0.0f;
		returnMat.m[2][3] = 0.0f;
		returnMat.m[3][3] = 1.0f;
		
		return true;
	}

	public static Matrix4X4 getTransformMat(float x, float y, float z) {
		return new Matrix4X4(1,0,0,0, 
				0,1,0,0, 
				0,0,1,0, 
				x,y,z,1);
	}
	
	public static Matrix4X4 getViewMatrix(Camera camera) {
		Vec4 n = Vec4.sub(camera.getTarget(), camera.getPos()); // z
		Vec4 u = Vec4.cross(new Vec4(0,1,0), n); // x
		Vec4 v = Vec4.cross(n, u); // y
		n.normalize();
		u.normalize();
		v.normalize();
		
		return new Matrix4X4(u.getX(), v.getX(), n.getX(), 0,
							 u.getY(), v.getY(), n.getY(), 0,
							 u.getZ(), v.getZ(), n.getZ(), 0,
							 -camera.getPos().getX(), -camera.getPos().getY(), -camera.getPos().getZ(), 1);
	}
	
	public static Matrix4X4 getScaleMatrix(float xScale, float yScale, float zScale) {
		return new Matrix4X4(
				xScale,0,0,0, 
				0,yScale,0,0, 
				0,0,zScale,0, 
				0,0,0,1);
	}
	
	public static Matrix4X4 getRotationMatrix(float x, float y, float z) {
		Matrix4X4 rx = new Matrix4X4();
		Matrix4X4 ry = new Matrix4X4();
		Matrix4X4 rz = new Matrix4X4();

		// z
		rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = (float)Math.sin(z);rz.m[0][2] = 0;				rz.m[0][3] = 0;
		rz.m[1][0] = -(float)Math.sin(z);rz.m[1][1] = (float)Math.cos(z);rz.m[1][2] = 0;				rz.m[1][3] = 0;
		rz.m[2][0] = 0;					 rz.m[2][1] = 0;					 rz.m[2][2] = 1;			rz.m[2][3] = 0;
		rz.m[3][0] = 0;					 rz.m[3][1] = 0;					 rz.m[3][2] = 0;			rz.m[3][3] = 1;

		// x
		rx.m[0][0] = 1;					rx.m[0][1] = 0;					rx.m[0][2] = 0;					rx.m[0][3] = 0;
		rx.m[1][0] = 0;					rx.m[1][1] = (float)Math.cos(x);rx.m[1][2] = -(float)Math.sin(x);rx.m[1][3] = 0;
		rx.m[2][0] = 0;					rx.m[2][1] = (float)Math.sin(x);rx.m[2][2] = (float)Math.cos(x);rx.m[2][3] = 0;
		rx.m[3][0] = 0;					rx.m[3][1] = 0;					rx.m[3][2] = 0;					rx.m[3][3] = 1;

		// y
		ry.m[0][0] = (float)Math.cos(y);ry.m[0][1] = 0;					ry.m[0][2] = -(float)Math.sin(y);ry.m[0][3] = 0;
		ry.m[1][0] = 0;					ry.m[1][1] = 1;					ry.m[1][2] = 0;					ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(y);ry.m[2][1] = 0;					ry.m[2][2] = (float)Math.cos(y);ry.m[2][3] = 0;
		ry.m[3][0] = 0;					ry.m[3][1] = 0;					ry.m[3][2] = 0;					ry.m[3][3] = 1;

		Matrix4X4 tempMat = Matrix4X4.multiplyMatrix(rx, ry);
		return Matrix4X4.multiplyMatrix(tempMat, rz);
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






















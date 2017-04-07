package com.math;

public class Quat {
	private float q0; // w
	private Vec3 qv; // x y z
	
	public Quat() {
		q0 = 0;
		qv = new Vec3();
	}
	
	public Quat(float q0, Vec3 qv) {
		this.q0 = q0;
		this.qv = qv;
	}
	
	public Quat(float w, float x, float y, float z) {
		q0 = w;
		qv.setX(x);
		qv.setY(y);
		qv.setZ(z);
	}
	
	public Quat(Vec3 qv) {
		q0 = 0;
		this.qv.copy(qv);
	}
	
	public Quat(Quat quat) {
		q0 = quat.q0;
		qv.copy(quat.qv);
	}
	
	public void copy(Quat quat) {
		q0 = quat.q0;
		qv.copy(quat.qv);
	}
	
	public void add(Quat rhs) {
		this.q0 += rhs.q0;
		this.qv.add(rhs.qv);
	}
	
	public static Quat add(Quat lhs, Quat rhs) {
		return new Quat(lhs.q0 + rhs.q0, Vec3.add(lhs.qv, rhs.qv));
	}
	
	public void sub(Quat rhs) {
		this.q0 -= rhs.q0;
		this.qv.sub(rhs.qv);
	}
	
	public static Quat sub(Quat lhs, Quat rhs) {
		return new Quat(lhs.q0 - rhs.q0, Vec3.sub(lhs.qv, rhs.qv));
	}
	
	// 共轭
	public Quat getConjugate() {
		return new Quat(q0, Vec3.scale(qv, -1));
	}
	
	public void scale(float scale) {
		q0 *= scale;
		qv.scale(scale);
	}
	
	public static Quat scale(Quat rhs, float scale) {
		return new Quat(rhs.q0 * scale, Vec3.scale(rhs.qv, scale));
	}
	
	public float getLength() {
		return (float)Math.sqrt(q0*q0 + qv.getX() * qv.getX() + qv.getY() * qv.getY() + qv.getZ() * qv.getZ());
	}
	
	public float getLength2() {
		return q0*q0 + qv.getX() * qv.getX() + qv.getY() * qv.getY() + qv.getZ() * qv.getZ();
	}
	
	public void normalize() {
		float length_inv = 1.0f / getLength();
		
		q0 *= length_inv;
		qv.scale(length_inv);
	}
	
	public static Quat normalize(Quat rhs) {
		float length_inv = 1.0f / rhs.getLength();
		return new Quat(rhs.q0 * length_inv,  Vec3.scale(rhs.qv, length_inv));
	}
	
	public void unitInverse() {
		qv.scale(-1);
	}
	
	public static Quat getUnitInverse(Quat rhs) {
		return new Quat(rhs.q0, Vec3.scale(rhs.qv, -1));
	}
	
	public void inverse() {
		float length2_inv = 1.0f / getLength2();
		
		q0 *= length2_inv;
		qv.scale(-length2_inv);
	}
	
	public static Quat inverse(Quat rhs) {
		float length2_inv = 1.0f / rhs.getLength2();
		return new Quat(rhs.q0 * length2_inv, Vec3.scale(rhs.qv, -length2_inv));
	}
	
	public static Quat multiply(Quat lhs, Quat rhs) {
		float w = lhs.q0 * rhs.q0 - lhs.qv.getX() * rhs.qv.getX() - lhs.qv.getY() * rhs.qv.getY() - lhs.qv.getZ() * rhs.qv.getZ();
		float x = lhs.q0 * rhs.qv.getX() + lhs.qv.getX() * rhs.q0 + lhs.qv.getY() * rhs.qv.getZ() - lhs.qv.getZ() * rhs.qv.getY();
		float y = lhs.q0 * rhs.qv.getY() - lhs.qv.getX() * rhs.qv.getZ() + lhs.qv.getY() * rhs.q0 - lhs.qv.getZ() * rhs.qv.getX();
		float z = lhs.q0 * rhs.qv.getZ() + lhs.qv.getX() * rhs.qv.getY() - lhs.qv.getY() * rhs.qv.getX() + lhs.qv.getZ() * rhs.q0;
		
		return new Quat(w, x, y, z);
	}
	
	// this function computes q1*q2*q3 in that order and returns 
	public static Quat tripleMultiply(Quat q1, Quat q2, Quat q3) {
		Quat qtmp =  Quat.multiply(q1, q2);
		return Quat.multiply(qtmp, q3);
	}
	
	public void vector3dThetaToQuat(Vec3 v, float thetaRads){
		float theta_div_2 = 0.5f * thetaRads;
		float sinf_theta = (float)Math.sin(theta_div_2);
		q0 = (float)Math.cos(theta_div_2);
		qv.copy(Vec3.scale(v, sinf_theta));
	}
	
	public void vector4dThetaToQuat(Vec4 v, float thetaRads){
		float theta_div_2 = 0.5f * thetaRads;
		float sinf_theta = (float)Math.sin(theta_div_2);
		q0 = (float)Math.cos(theta_div_2);
		Vec4 tempV = Vec4.scale(v, sinf_theta);
		qv = new Vec3(tempV.getX(), tempV.getY(), tempV.getZ());
	}
	
	// x y z vec3 w:theta
	public Vec4 quatToVec3Theta() {
		Vec4 result = new Vec4();
		float theta = (float)Math.acos(q0);
		float sinf_theta_inv = 1.0f / (float)Math.sin(theta);
		result.setX(qv.getX() * sinf_theta_inv);
		result.setY(qv.getY() * sinf_theta_inv);
		result.setZ(qv.getZ() * sinf_theta_inv);
		result.setW(theta * 2.0f);
		return result;
	}
	
	public void eulerZYXToQuat(float thetaRadsZ, float thetaRadsY, float thetaRadsX) {
		float cos_z_2 = (float)Math.cos(thetaRadsZ * 0.5);
		float cos_y_2 = (float)Math.cos(thetaRadsY * 0.5);
		float cos_x_2 = (float)Math.cos(thetaRadsX * 0.5);

		float sin_z_2 = (float)Math.sin(thetaRadsZ * 0.5);
		float sin_y_2 = (float)Math.sin(thetaRadsY * 0.5);
		float sin_x_2 = (float)Math.sin(thetaRadsX * 0.5);
		
		q0 = cos_z_2*cos_y_2*cos_x_2 + sin_z_2*sin_y_2*sin_x_2;
		qv.setX(cos_z_2*cos_y_2*sin_x_2 - sin_z_2*sin_y_2*cos_x_2);
		qv.setY(cos_z_2*sin_y_2*cos_x_2 + sin_z_2*cos_y_2*sin_x_2);
		qv.setZ(sin_z_2*cos_y_2*cos_x_2 - cos_z_2*sin_y_2*sin_x_2);
	}
	
	public void print() {
		System.out.print("Quat x: " + qv.getX() + " y: " + qv.getY() + " Z: " + qv.getZ() + " w: " + q0);
	}
	
	public float getQ0() {
		return q0;
	}
	public void setQ0(float q0) {
		this.q0 = q0;
	}
	public Vec3 getQv() {
		return qv;
	}
	public void setQv(Vec3 qv) {
		this.qv = qv;
	}
	
}


















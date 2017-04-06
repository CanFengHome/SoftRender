package com.math;

public class Quat {
	private float q0;
	private Vec3 qv;
	
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


















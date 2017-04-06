package com.math;

public class Camera {
	private Vec4 pos;
	private Vec4 target;
	
	public Camera(Vec4 pos, Vec4 target) {
		this.pos = pos;
		this.target = target;
	}
	
	public Vec4 getPos() {
		return pos;
	}
	public void setPos(Vec4 pos) {
		this.pos = pos;
	}
	public Vec4 getDir() {
		return target;
	}
	public void setDir(Vec4 dir) {
		this.target = dir;
	}

	private Vec4 u; // x
	private Vec4 v; // y
	private Vec4 n; // z
	private float near;
	private float far;
	private float viewWidth;
	private float viewHeight;
	private float aspectRatio;
	private float fov;
	
	private float viewCenterX;
	private float viewCenterY;
	
	private float viewPlaneWidth;
	private float viewPlaneHeight;
	private float viewDist; // d
	
	private Matrix4X4 mCam; // world -> camera
	private Matrix4X4 mPer; // camera -> perspective
	private Matrix4X4 mScr; // perspective -> screen
	
	Plane rightClipPlane;
	Plane leftClipPlane;
	Plane topClipPlane;
	Plane bottomPlane;
	
	public void initPerspectiveVeiw(float fov, float near, float far, int width, int height) {
		u = new Vec4(1,0,0);
		v = new Vec4(0,1,0);
		n = new Vec4(0,0,1);
		
		this.near = near;
		this.far = far;
		
		this.viewWidth = width;
		this.viewHeight = height;
		viewCenterX = (viewWidth - 1) / 2;
		viewCenterY = (viewHeight - 1) / 2;
		
		this.aspectRatio = viewWidth / viewHeight;
		this.fov = fov;
		
		viewPlaneWidth = 2.0f;
		viewPlaneHeight = 2.0f / aspectRatio;
		
		float tanFovDiv2 = (float)(Math.tan(MathUtil.DegToRad(fov/2.0f)));
		viewDist = (0.5f * viewPlaneWidth) * tanFovDiv2;
		
		mCam = new Matrix4X4();
		mCam.identity();
		mPer = new Matrix4X4();
		mPer.identity();
		mScr = new Matrix4X4();
		mScr.identity();
		
		// fov == 90
		rightClipPlane = new Plane(new Vec3(0,0,0), new Vec3(1,0,-1));
		leftClipPlane = new Plane(new Vec3(0,0,0), new Vec3(-1, 0, -1));
		topClipPlane = new Plane(new Vec3(0, 0, 0), new Vec3(0, 1, -1));
		bottomPlane = new Plane(new Vec3(0, 0, 0), new Vec3(0, -1, -1));
	}
}




























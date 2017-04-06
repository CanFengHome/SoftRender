package com.test;

import com.math.Camera;
import com.math.Matrix4X4;
import com.math.Trangle;
import com.math.Vec4;
import com.sharp.Bitmap;
import com.sharp.GameApplication;
import com.sharp.IGameObject;

/*
 * 绘制三角形
 */
public class Test1 implements IGameObject {
	private Camera camera;
	
	private Trangle trangle;
	private Vec4 tranglePos;
	private Matrix4X4 trangleRotationMat;
	
	@Override
	public void initData() {
		trangle = new Trangle(new Vec4(0.0f, 50.0f, 0.0f), 
				new Vec4(50.0f, -50.0f, 0.0f), 
				new Vec4(-50.0f, -50.0f, 0.0f));
		
		camera = new Camera(new Vec4(0.0f, 0.0f, -100.0f), new Vec4(0.0f, 0.0f, 0.0f));
		int width = GameApplication.getInstance().getWidth();
		int height = GameApplication.getInstance().getHeight();
		camera.initPerspectiveVeiw(90.0f, 50.0f, 500.0f, width, height);
		
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Bitmap frameBuffer) {

	}

	@Override
	public void clear() {
		
	}

}












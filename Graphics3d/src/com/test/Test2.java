package com.test;

import com.math.Camera;
import com.math.Matrix4X4;
import com.math.Vec4;
import com.sharp.Bitmap;
import com.sharp.Color3B;
import com.sharp.GameApplication;
import com.sharp.GraphicsTool;
import com.sharp.IGameObject;
import com.sharp.Vec2i;

/*
 * 旋转三角形
 */
public class Test2 implements IGameObject {
	private Camera camera;
	
	private Vec4 trangle[];
	private Vec4 tranglePos;
	private float angleY = 0.0f;
	
	@Override
	public void initData() {
		trangle = new Vec4[]{
				new Vec4(0.0f, 50.0f, 0.0f), 
				new Vec4(50.0f, -50.0f, 0.0f), 
				new Vec4(-50.0f, -50.0f, 0.0f)};
		
		camera = new Camera(new Vec4(0.0f, 0.0f, -100.0f), new Vec4(0.0f, 0.0f, 0.0f));
		int width = GameApplication.getInstance().getWidth();
		int height = GameApplication.getInstance().getHeight();
		camera.initPerspectiveVeiw(90.0f, 50.0f, 500.0f, width, height);
		
		tranglePos = new Vec4(0, 0, 100);
	}

	@Override
	public void update(float delta) {
		angleY += 0.01;
		if (angleY >= 360.0f) {
			angleY = 0.0f;
		}
	}

	@Override
	public void render(Bitmap frameBuffer) {
		Matrix4X4 rotateMat = Matrix4X4.getRotationMatrix(0, angleY, 0);
		Matrix4X4 modelMat = Matrix4X4.getTransformMat(tranglePos.getX(), tranglePos.getY(), tranglePos.getZ());
		modelMat = Matrix4X4.multiplyMatrix(rotateMat, modelMat);
		
		Matrix4X4 viewMat = Matrix4X4.getViewMatrix(camera);
		Matrix4X4 mat = Matrix4X4.multiplyMatrix(modelMat, viewMat);
		
		float alpha = 0.5f * camera.getViewPortWidth() - 0.5f;
		float beta = 0.5f * camera.getViewPortHeight() - 0.5f;
		
		Vec2i arrPts[] = new Vec2i[3];
		
		for (int i=0; i<3; ++i) {
			Vec4 pt = trangle[i];
			Vec4 viewPt = Matrix4X4.vec4MultiplyMat4X4(pt, mat);
			// view to perspective
			float z = viewPt.getZ();
			float x = camera.getViewDist() * viewPt.getX() / z;
			float y = camera.getViewDist() * viewPt.getY() / z;
			// perspective to screen
			x = alpha + alpha * x;
			y = beta - beta * y;
			
			arrPts[i] = new Vec2i((int)x, (int)y);
		}
		
		Color3B color = new Color3B((byte)0, (byte)255, (byte)0);
		GraphicsTool.drawLineBresenham(arrPts[0], arrPts[1], color, frameBuffer);
		GraphicsTool.drawLineBresenham(arrPts[1], arrPts[2], color, frameBuffer);
		GraphicsTool.drawLineBresenham(arrPts[2], arrPts[0], color, frameBuffer);
	}

	@Override
	public void clear() {
		
	}

}












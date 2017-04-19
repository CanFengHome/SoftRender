package com.test;

import java.io.IOException;

import com.engine.EngineApp;
import com.engine.math.Matrix4f;
import com.render.Bitmap;
import com.render.IRenderScene;
import com.render.Mesh;
import com.render.RenderContext;

public class TestScene05 implements IRenderScene {
	private Bitmap texture;
	private Mesh mesh;
	private Matrix4f projection;
	
	@Override
	public void initData() {
		float winWidth = EngineApp.getInstance().getWinWidth();
		float winHeight = EngineApp.getInstance().getWinHeight();
		
		projection = new Matrix4f().InitPerspective((float)Math.toRadians(90.0f),
				winWidth/winHeight, 0.1f, 1000.0f);
		
		try {
			texture = new Bitmap("./res/bricks.jpg");
			mesh = new Mesh("./res/icosphere.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	float rotCounter = 0.0f;
	@Override
	public void update(float delta) {
		rotCounter += delta / 5000.0f;
	}

	@Override
	public void render(RenderContext frameBuffer) {
		Matrix4f translation = new Matrix4f().InitTranslation(0.0f, 0.0f, 3.0f - 3 * (float)Math.sin(rotCounter));
		Matrix4f rotation = new Matrix4f().InitRotation(rotCounter, 0.0f, rotCounter);
		Matrix4f transform = projection.Mul(translation.Mul(rotation));

		frameBuffer.clear((byte)0x00);
		frameBuffer.ClearDepthBuffer();
		
		mesh.Draw(frameBuffer, transform, texture);
	}

	@Override
	public void clear() {
		
	}

}













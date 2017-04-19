package com.test;

import com.engine.EngineApp;
import com.engine.math.Matrix4f;
import com.render.IRenderScene;
import com.render.RenderContext;
import com.render.Vertex;

public class TestScene03 implements IRenderScene {
	private Vertex minYVert = new Vertex(-1, -1, 0);
	private Vertex midYVert = new Vertex(0, 1, 0);
	private Vertex maxYVert = new Vertex(1, -1, 0);
	private Matrix4f projection;
	
	private float rotCounter = 0.0f;
	
	@Override
	public void initData() {
		float winWidth = EngineApp.getInstance().getWinWidth();
		float winHeight = EngineApp.getInstance().getWinHeight();
		
		projection = new Matrix4f().InitPerspective((float)Math.toRadians(90.0f),
				winWidth/winHeight, 0.1f, 1000.0f);
	}

	@Override
	public void update(float delta) {
		rotCounter += delta / 1000.0f;
		if (rotCounter > Math.PI) {
			rotCounter -= Math.PI;
		}
	}

	@Override
	public void render(RenderContext frameBuffer) {
		frameBuffer.clear((byte)0x00);

		Matrix4f translation = new Matrix4f().InitTranslation(0.0f, 0.0f, 2.0f);
		Matrix4f rotation = new Matrix4f().InitRotation(0.0f, rotCounter, 0.0f);
		Matrix4f transform = projection.Mul(translation.Mul(rotation));
		
//		frameBuffer.fillTriangle(maxYVert.Transform(transform), 
//				midYVert.Transform(transform), minYVert.Transform(transform));
	}

	@Override
	public void clear() {

	}
}

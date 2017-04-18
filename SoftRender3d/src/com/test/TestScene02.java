package com.test;

import com.render.IRenderScene;
import com.render.RenderContext;
import com.render.Vertex;

public class TestScene02 implements IRenderScene {
	private Vertex minYVert = new Vertex(100, 100, 0.0f);
	private Vertex midYVert = new Vertex(150, 160, 0.0f);
	private Vertex maxYVert = new Vertex(80, 260, 0.0f);
	
	@Override
	public void initData() {
		
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(RenderContext frameBuffer) {
		frameBuffer.clear((byte)0x00);

		frameBuffer.fillTriangle(minYVert, midYVert, maxYVert);

	}

	@Override
	public void clear() {

	}

}

package com.render;

public interface IRenderScene {
	void initData();
	void update();
	void render(RenderContext frameBuffer);
	void clear();
}

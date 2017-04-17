package com.render;

public interface IRenderScene {
	void initData();
	void update(float delta);
	void render(RenderContext frameBuffer);
	void clear();
}

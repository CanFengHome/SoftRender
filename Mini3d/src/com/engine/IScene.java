package com.engine;

public interface IScene {
	void initData();
	void update(float delta);
	void render(Bitmap frameBuffer);
	void clear();
}

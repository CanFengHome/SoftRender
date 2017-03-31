package com.sharp;

public interface IGameObject {
	void initData();
	void update(float delta);
	void render(Bitmap frameBuffer);
	void clear();
}

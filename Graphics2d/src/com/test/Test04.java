package com.test;

import com.sharp.Bitmap;
import com.sharp.Color3B;
import com.sharp.GraphicsTool;
import com.sharp.IGameObject;

public class Test04 implements IGameObject{
	
	@Override
	public void initData() {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Bitmap frameBuffer) {
		GraphicsTool.drawLineBresenham(100, 100, 300, 300, Color3B.RED, frameBuffer);
		GraphicsTool.drawLineBresenham(100, 100, 100, 300, Color3B.BLUE, frameBuffer);
		GraphicsTool.drawLineBresenham(100, 100, 300, 100, Color3B.GREEN, frameBuffer);
	}

	@Override
	public void clear() {
	}

}

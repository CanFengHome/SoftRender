package com.test;

import java.awt.Color;
import java.awt.Graphics;

import com.sharp.Bitmap;
import com.sharp.GameApplication;
import com.sharp.IGameObject;

/*
 * 仅为验证框架是否合理, 是否能够正确显示要绘制的内容
 */
public class Test01 implements IGameObject {

	@Override
	public void initData() {

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Bitmap frameBuffer) {
		int width = GameApplication.getInstance().getWidth();
		int height = GameApplication.getInstance().getHeight();
		
		final int OFFSET_HALF = 30;
		int startX = width / 2 - OFFSET_HALF;
		int startY = height / 2 - OFFSET_HALF;
		
		for (int y=0; y < OFFSET_HALF * 2; ++y) {
			for (int x=0; x < OFFSET_HALF * 2; ++x) {
				frameBuffer.DrawPixel(startX + x, startY + y, (byte)255, (byte)255, (byte)0, (byte)0);
			}
		}
	}

	@Override
	public void clear() {
		
	}
}












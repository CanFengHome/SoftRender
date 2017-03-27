package com.test;

import java.awt.Graphics;
import java.util.Random;

import com.sharp.Bitmap;
import com.sharp.GameApplication;
import com.sharp.IGameObject;

/*
 * window游戏开发大师技巧2d demo4_1
 */
public class Test02 implements IGameObject {

	@Override
	public void initData() {

	}

	@Override
	public void update(float delta) {

	}

	private Random rd = new Random();
	@Override
	public void render(Bitmap frameBuffer) {
		int width = GameApplication.getInstance().getWidth();
		int height = GameApplication.getInstance().getHeight();
		
		for (int index = 0; index < 1000; ++index) {
			int x = rd.nextInt(width);
			int y = rd.nextInt(height);
			
			frameBuffer.DrawPixel(x, y, (byte)255, (byte)rd.nextInt(255), (byte)rd.nextInt(255), (byte)rd.nextInt(255));
		}
	}

	@Override
	public void clear() {

	}
}

















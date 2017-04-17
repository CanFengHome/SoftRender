package com.test;

import com.engine.EngineApp;
import com.render.IRenderScene;
import com.render.RenderContext;

public class TestScene01 implements IRenderScene {
	private final int POINT_NUM = 100;
	private final float RANGE = 32.0f; // 散开直径
	private final float SPEED = 20.0f;
	
	private final float pointsX[];
	private final float pointsY[];
	private final float pointsZ[];
	
	public TestScene01() {
		pointsX = new float[POINT_NUM];
		pointsY = new float[POINT_NUM];
		pointsZ = new float[POINT_NUM];
	}
	
	@Override
	public void initData() {
		for (int i=0; i<POINT_NUM; ++i) {
			resetPoint(i);
		}
	}

	private void resetPoint(int index) {
		if (index >= POINT_NUM) {
			return;
		}
		
		pointsX[index] = 2 * ((float)Math.random() - 0.5f) * RANGE;
		pointsY[index] = 2 * ((float)Math.random() - 0.5f) * RANGE;
		pointsZ[index] = ((float)Math.random() + 0.0001f) * RANGE;
	}
	
	@Override
	public void update(float delta) {
		float timeDelta = delta / 1000.0f;
		for (int i=0; i<POINT_NUM; ++i) {
			pointsZ[i] -= timeDelta * SPEED;
			if (pointsZ[i] <= 0.0f) {
				resetPoint(i);
			}
		}
	}

	@Override
	public void render(RenderContext frameBuffer) {
		frameBuffer.clear((byte)0x00);
		
		float winHalfWidth = EngineApp.getInstance().getWinWidth() / 2;
		float winHalfHeight = EngineApp.getInstance().getWinHeight() / 2;

		for (int i=0; i<POINT_NUM; ++i) {
			int x = (int)(pointsX[i] / pointsZ[i] * winHalfWidth + winHalfWidth);
			int y = (int)(pointsY[i] / pointsZ[i] * -winHalfHeight + winHalfHeight); // 屏幕Y方向和三维右手坐标Y方向相反
			
			if (x < 0 || x >= EngineApp.getInstance().getWinWidth() || 
				y < 0 || y >= EngineApp.getInstance().getWinHeight() ) {
				resetPoint(i);
			} else {
				frameBuffer.drawPixel(x, y, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
			}
		}
	}

	@Override
	public void clear() {

	}

}

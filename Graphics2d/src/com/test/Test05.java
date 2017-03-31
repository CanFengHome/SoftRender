package com.test;

import java.util.Random;

import com.sharp.Bitmap;
import com.sharp.Color3B;
import com.sharp.GraphicsTool;
import com.sharp.IGameObject;
import com.sharp.Trangle;
import com.sharp.Vec2i;

public class Test05 implements IGameObject{
	private Trangle trangle1;
	private Trangle trangle2;
	private Trangle trangle3;
	@Override
	public void initData() {
		trangle1 = new Trangle(new Vec2i(100, 50), new Vec2i(150, 200), new Vec2i(50, 200));
		trangle2 = new Trangle(new Vec2i(150, 50), new Vec2i(250, 50), new Vec2i(200, 200));
		
		trangle3 = new Trangle(new Vec2i(350, 50), new Vec2i(300, 200), new Vec2i(400, 400));
		
//		Random rd = new Random();
//		int x1 = rd.nextInt(20) + 10;
//		int x2 = rd.nextInt(50) + 200;
//		int x3 = rd.nextInt(50) + 400;
//		int y1 = rd.nextInt(10) + 10;
//		int y2 = rd.nextInt(20) + 200;
//		int y3 = rd.nextInt(100) + 300;
//		trangle3 = new Trangle(new Vec2i(x1, y1), new Vec2i(x2, y2), new Vec2i(x3, y3));
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Bitmap frameBuffer) {
		GraphicsTool.fillTrangle(trangle1, Color3B.BLUE, frameBuffer);
		GraphicsTool.fillTrangle(trangle2, Color3B.RED, frameBuffer);
		GraphicsTool.fillTrangle(trangle3, Color3B.GREEN, frameBuffer);
//		GraphicsTool.drawTrangle(trangle1, Color3B.RED, frameBuffer);
	}

	@Override
	public void clear() {
	}

}

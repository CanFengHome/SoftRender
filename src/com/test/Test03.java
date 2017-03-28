package com.test;

import com.sharp.Bitmap;
import com.sharp.IGameObject;

public class Test03 implements IGameObject{
	private Bitmap m_bitmap;
	@Override
	public void initData() {
		m_bitmap = new Bitmap("./res/texture/bitmap24.bmp");
//		m_bitmap = new Bitmap("./res/texture/test03.png");
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Bitmap frameBuffer) {
		frameBuffer.BitBitmap(100, 100, 0, 0, m_bitmap);
	}

	@Override
	public void clear() {

	}

}

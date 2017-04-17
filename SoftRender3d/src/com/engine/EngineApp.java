package com.engine;

import java.awt.event.KeyEvent;

import com.render.IRenderScene;

public class EngineApp {
	private EngineApp() {

	}

	private static EngineApp s_app = new EngineApp();

	public static EngineApp getInstance() {
		return s_app;
	}

	private EngineWindow window;

	public void createWindow(int windth, int height, String title) {
		window = new EngineWindow(windth, height, title);
	}

	private final float FixedDelta = 1000 / 60; // 毫秒
	public void run(IRenderScene scene) {
		if (scene == null)
			return;

		scene.initData();

		long previousTime = System.nanoTime();
		while (true) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime) / 1000000.0); // 毫秒
			if (delta < FixedDelta) {
				try {
					Thread.sleep((long) (FixedDelta - delta));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			} else {
				previousTime = currentTime;
			}

			scene.update();

			window.getFrameBuffer().clear((byte) 0x80);
			scene.render(window.getFrameBuffer());
			window.swapBuffers();

			if (window.getInput().getKey(KeyEvent.VK_ESCAPE)) {
				break;
			}
		}

		scene.clear();
	}

	public int getWinWidth() {
		return window.getWinWidth();
	}

	public int getWinHeight() {
		return window.getWinHeight();
	}
}












package com.engine;

import java.awt.event.KeyEvent;

public class GameApplication {
	private static GameApplication s_app = new GameApplication();
	public static GameApplication getInstance() {
		return s_app;
	}
	
	private int m_width;
	private int m_height;
	private GameWindow m_window = null;
	public GameWindow getGameWindow() {
		return m_window;
	}
	public void init(int width, int height, String title) {
		m_width = width;
		m_height = height;
		
		m_window = new GameWindow(width, height, title);
	}

	private final float fixedDelta = 1000 / 60;
	public void run(IScene scene) {
		scene.initData();

		long previousTime = System.nanoTime();
		long totalTime = 0;
		while (true) {
			long currentTime = System.nanoTime();
			float delta = (float)((currentTime - previousTime)/1000000.0); // 毫秒
			
			if (delta < fixedDelta) {
				try {
					Thread.sleep((long)(fixedDelta - delta));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			} else {
				previousTime = currentTime;
				totalTime += delta;
			}
			
			scene.update(totalTime);
			
			m_window.getFrameBuffer().Clear((byte) 0x80);
			scene.render(m_window.getFrameBuffer());

			m_window.swapBuffers();

			if (m_window.getInput().GetKey(KeyEvent.VK_ESCAPE)) {
				break;
			}
		}

		scene.clear();
	}
	
	public int getWidth() {
		return m_width;
	}
	
	public int getHeight() {
		return m_height;
	}
}

package com.sharp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameApplication {
	private static GameApplication s_app = new GameApplication();
	private GameWindow m_window = null;
	private int m_width;
	private int m_height;
	
	private List<IGameObject> m_gameObjs;
	
	public int getWidth() {
		return m_width;
	}
	
	public int getHeight() {
		return m_height;
	}
	
	public GameWindow getGameWindow() {
		return m_window;
	}
	
	public static GameApplication getInstance() {
		return s_app;
	}

	public void init(int width, int height, String title) {
		m_width = width;
		m_height = height;
		m_gameObjs = new ArrayList<IGameObject>();
		m_window = new GameWindow(width, height, title);
	}
	
	public void addGameObject(IGameObject gameObj) {
		m_gameObjs.add(gameObj);
	}
	
	public void run() {
		for (IGameObject objItem : m_gameObjs) {
			objItem.initData();
		}
		
		long previousTime = System.nanoTime();
		final float fixedDelta = 1000 / 60;
		while (true) {
			long currentTime = System.nanoTime();
			float delta = (float)((currentTime - previousTime)/1000000.0); // 毫秒
			
			if (delta < fixedDelta)
			{
				try {
					Thread.sleep((long)(fixedDelta - delta));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			else
			{
				previousTime = currentTime;
			}
			
			for (IGameObject objItem : m_gameObjs) {
				objItem.update(0.0f);
			}
			
			for (IGameObject objItem : m_gameObjs) {
				objItem.render(m_window.getFrameBuffer());
			}
			
			m_window.swapBuffers();
			
			if (m_window.getInput().GetKey(KeyEvent.VK_ESCAPE)) {
				break;
			}
		}
		
		for (IGameObject objItem : m_gameObjs) {
			objItem.clear();
		}
	}

	public void drawGraphicsContent() {
		Graphics gfx = m_window.getDisplayGraphics();
		
		for (IGameObject objItem : m_gameObjs) {
			if (objItem instanceof IDrawGraphics) {
				IDrawGraphics drawObj = (IDrawGraphics)(objItem);
				drawObj.drawGraphicsContent(gfx);
			}
		}
	}



}

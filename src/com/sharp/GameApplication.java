package com.sharp;

public class GameApplication {
	private static GameApplication s_app = new GameApplication();
	private GameWindow m_window = null;
	
	public static GameApplication getInstance() {
		return s_app;
	}

	public void init(int width, int height, String title) {
		m_window = new GameWindow(width, height, title);
	}
	
	public void run() {
		
	}





}

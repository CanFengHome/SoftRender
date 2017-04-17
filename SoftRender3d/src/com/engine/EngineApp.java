package com.engine;

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
	
	public void run() {
		
	}
	
	
	
	
	public int getWinWidth() {
		return window.getWinWidth();
	}
	public int getWinHeight() {
		return window.getWinHeight();
	}
}

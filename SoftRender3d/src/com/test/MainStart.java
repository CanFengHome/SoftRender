package com.test;

import com.engine.EngineApp;

public class MainStart {
	public static void main(String[] args) {
		EngineApp.getInstance().createWindow(400, 300, "soft render");
		
		
		EngineApp.getInstance().run();
	}
}

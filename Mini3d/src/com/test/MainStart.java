package com.test;

import com.engine.GameApplication;
import com.engine.IScene;

public class MainStart {

	public static void main(String[] args) {
		GameApplication app = GameApplication.getInstance();
		app.init(400, 300, "Game2d");
		
		IScene testScene = new TestScene01();
		app.run(testScene);
	}
}


















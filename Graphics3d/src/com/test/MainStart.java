package com.test;

import com.sharp.GameApplication;

public class MainStart {

	public static void main(String[] args) {
		GameApplication app = GameApplication.getInstance();
		app.init(400, 300, "Game2d");
		app.addGameObject(new Test1());
		app.run();
	}

}

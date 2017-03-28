package com.sharp;

import com.test.Test01;
import com.test.Test02;
import com.test.Test03;

public class MainStart {

	public static void main(String[] args) {
		GameApplication app = GameApplication.getInstance();
		app.init(640, 480, "Game2d");
		
//		app.addGameObject(new Test01());
//		app.addGameObject(new Test02());
		app.addGameObject(new Test03());
		
		app.run();
	}

}

package com.sharp;

import com.test.Test01;
import com.test.Test02;

public class MainStart {

	public static void main(String[] args) {
		GameApplication app = GameApplication.getInstance();
		app.init(400, 300, "Game2d");
		
//		app.addGameObject(new Test01());
		app.addGameObject(new Test02());
		
		app.run();
	}

}

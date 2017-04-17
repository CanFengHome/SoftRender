package com.test;

import com.engine.EngineApp;
import com.render.IRenderScene;

public class MainStart {
	public static void main(String[] args) {
		EngineApp.getInstance().createWindow(400, 300, "soft render");
		
		IRenderScene scene = new TestScene01();
		
		EngineApp.getInstance().run(scene);
	}
}

package com.test;

import com.engine.EngineApp;
import com.render.IRenderScene;

public class MainStart {
	public static void main(String[] args) {
		EngineApp.getInstance().createWindow(400, 300, "soft render");
		
//		IRenderScene scene = new TestScene01(); // draw point
//		IRenderScene scene = new TestScene02(); // fill trangle
//		IRenderScene scene = new TestScene03(); // model view project screen
		IRenderScene scene = new TestScene04(); // fill gradual
		
		EngineApp.getInstance().run(scene);
	}
}

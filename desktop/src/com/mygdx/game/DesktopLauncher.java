package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("SnakeGame");
		config.setWindowedMode(720, 720);
		config.setResizable(false);
		config.setWindowIcon("icon.png");
		new Lwjgl3Application(new MyGame(), config);
	}
}

package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game {

	@Override
	public void create() {
		// Define a tela inicial como MenuScreen
		setScreen(new MenuScreen(this));
	}
}

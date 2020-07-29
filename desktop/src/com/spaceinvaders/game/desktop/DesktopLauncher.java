package com.spaceinvaders.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spaceinvaders.game.SpaceInvaders;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = SpaceInvaders.GAME_HEIGHT;
		config.width = SpaceInvaders.GAME_WIDTH;
		new LwjglApplication(new SpaceInvaders(), config);
	}
}

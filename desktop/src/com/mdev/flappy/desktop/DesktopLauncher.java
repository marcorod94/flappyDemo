package com.mdev.flappy.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mdev.flappy.FlappyDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyDemo.WIDTH;
		config.height = FlappyDemo.HEIGHT;
		config.title = FlappyDemo.TITTLE;
		config.addIcon("icons/fb_ico_128.png", Files.FileType.Internal);
		config.addIcon("icons/fb_ico_32.png", Files.FileType.Internal);
		config.addIcon("icons/fb_ico_16.png", Files.FileType.Internal);
		config.backgroundFPS = 60;
		config.resizable = false;
		new LwjglApplication(new FlappyDemo(), config);

	}
}

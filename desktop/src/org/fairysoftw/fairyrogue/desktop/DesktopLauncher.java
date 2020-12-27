package org.fairysoftw.fairyrogue.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.fairysoftw.fairyrogue.FairyRogue;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT);
		config.useVsync(false);
		config.setResizable(false);
		new Lwjgl3Application(new FairyRogue(), config);
	}
}

package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.*;
import org.fairysoftw.fairyrogue.screen.MainScreen;
import org.fairysoftw.fairyrogue.screen.MenuScreen;
import org.fairysoftw.fairyrogue.screen.SettingScreen;

public class FairyRogue extends Game {
    public static final int VIRTUAL_WIDTH = 960;
    public static final int VIRTUAL_HEIGHT = 768;
    Screen mainScreen;
    Screen menuScreen;
    Screen settingScreen;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.load();
        mainScreen = new MainScreen(this);
        menuScreen = new MenuScreen(this);
        settingScreen = new SettingScreen(this);
        setScreen(mainScreen);
    }

    @Override
    public void dispose() {
        Assets.dispose();
        mainScreen.dispose();
    }
}

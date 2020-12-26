package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.*;
import org.fairysoftw.fairyrogue.screen.MainScreen;

public class FairyRogue extends Game {
    public static final int VIRTUAL_WIDTH = 960;
    public static final int VIRTUAL_HEIGHT = 768;
    Screen mainScreen;

    @Override
    public void create() {
        Assets.load();
        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }

    @Override
    public void dispose() {
        Assets.dispose();
        mainScreen.dispose();
    }
}

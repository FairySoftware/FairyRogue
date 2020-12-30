package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.screen.*;
import org.json.JSONObject;

import java.util.List;

/**
 * Game class is An ApplicationListener that delegates to a Screen.
 * This allows an application to easily have multiple screens.
 * FairyRogue class extends Game class, and implemented more methods.
 */
public class FairyRogue extends Game {
    public static final int VIRTUAL_WIDTH = 960;
    public static final int VIRTUAL_HEIGHT = 768;
    private Array<TiledMap> maps;
    private String settingExitType;
    private Screen mainScreen;
    private Screen settingScreen;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.load();
        setScreen(new EnterScreen(this));
    }

    /**
     * load the game MapsPath
     */
    private void loadGameMapsPath() {
        FileHandle handle = Gdx.files.local("game.json");
        String content = handle.readString();
        JSONObject gameJson = new JSONObject(content);
        List<Object> paths = gameJson.getJSONObject("game").getJSONArray("maps").toList();
        maps = new Array<TiledMap>();
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        for (Object a : paths) {
            maps.add(tmxMapLoader.load(String.valueOf(a)));
        }
    }

    /**
     * Show the specified screen
     *
     * @param screen the screen instance who called this method.
     * @param type   the screen that want to show.
     */
    public void showScreen(Screen screen, String type) {

        switch (type) {
            case "startScreen":
                setScreen(new StartScreen(this));
                break;
            case "mainScreen":
                loadGameMapsPath();
                mainScreen = new MainScreen(this, maps, null);
            case "mainScreen_continue":
                setScreen(mainScreen);
                break;
            case "settingScreen":
                settingExitType = "startScreen";
                if (settingScreen == null)
                    settingScreen = new SettingScreen(this);
                setScreen(settingScreen);
                break;
            case "settingScreenInGame":
                settingExitType = "poseScreen";
                if (settingScreen == null)
                    settingScreen = new SettingScreen(this);
                setScreen(settingScreen);
                break;
            case "poseScreen":
                setScreen(new PauseScreen(this));
                break;
            case "aboutScreen":
                setScreen(new AboutScreen(this));
                break;
            case "exit":
                Gdx.app.exit();
            default:
                return;
        }

        // Since EnterScreen can only be displayed when the game starts,
        // it doesn't need to be displayed later,
        // So after starting EnterScreen,
        // manually call the dispose () method of EnterScreen to destroy the start scene.
        if (screen instanceof EnterScreen) {
            screen.dispose();
        }
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }

    /**
     * get the settingScreen's return type,
     * make sure the settingScreen can return the right screen.
     *
     * @return String parameter. the exit type of screen.
     */
    public String getSettingExitType() {
        return this.settingExitType;
    }

//    public void restartGame() {
//        mainScreen.dispose();
//        mainScreen = new MainScreen(this);
//    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        if (screen instanceof MainScreen)
            mainScreen = screen;
    }
}
package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.screen.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class FairyRogue extends Game {
    public static final int VIRTUAL_WIDTH = 960;
    public static final int VIRTUAL_HEIGHT = 768;
    private Array<TiledMap> maps;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.load();
        setScreen(new EnterScreen(this));
    }

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
     * 开始场景展示完毕后调用该方法切换到主游戏场景
     */
    public void showScreen(Screen screen, String type) {

        // 设置当前场景为主游戏场景
        switch (type) {
            case "startScreen":
                setScreen(new StartScreen(this));
                break;
            case "mainScreen":
                loadGameMapsPath();
                setScreen(new MainScreen(this, maps, null));
                break;
            case "settingScreen":
                setScreen(new SettingScreen(this));
                break;
            case "aboutScreen":
                setScreen(new AboutScreen(this));
                break;
            case "exit":
                Gdx.app.exit();
            default:
                return;
        }

        if (screen instanceof EnterScreen) {
            // 由于 EnterScreen 只有在游戏启动时展示一下, 之后都不需要展示,
            // 所以启动完 GameScreen 后手动调用 AboutScreen 的 dispose() 方法销毁开始场景。
            screen.dispose();
        }
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}

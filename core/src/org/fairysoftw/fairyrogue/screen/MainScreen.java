package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.stage.GameStage;
import org.json.JSONObject;

import java.util.List;

public class MainScreen extends ScreenAdapter {
    private GameStage stage;
    private Array<TiledMap> maps;
    private int stageNumber = 0;

    public MainScreen(Game game) {
        loadGameMapsPath();
        nextStage();
    }

    private void loadGameMapsPath() {
        FileHandle handle = Gdx.files.local("game.json");
        String content = handle.readString();
        System.out.println(content);
        JSONObject gameJson = new JSONObject(content);
        List<Object> paths = gameJson.getJSONObject("game").getJSONArray("maps").toList();
        maps = new Array<TiledMap>();
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        for (Object a : paths) {
            maps.add(tmxMapLoader.load(String.valueOf(a)));
        }
    }

    private void nextStage() {
        if (stageNumber <= maps.size) {
            this.stage = new GameStage(maps.get(stageNumber), this.stage != null ? this.stage.getPlayerActor() : null);
            stageNumber++;
        }
        else {
            gameClear();
        }
    }

    private void gameClear() {
        Gdx.app.exit();
    }

    @Override
    public void show() {
        stage.draw();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.278f, 0.176f, 0.235f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (stage.isClear()) {
            nextStage();
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}



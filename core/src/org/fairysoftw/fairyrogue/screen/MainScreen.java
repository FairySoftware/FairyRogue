package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.stage.GameStage;

public class MainScreen extends ScreenAdapter {
    public static GameStage stage;

    public MainScreen(Game game) {
        stage = new GameStage(Assets.map, null);
    }

    @Override
    public void show() {
        stage.draw();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.278f, 0.176f, 0.235f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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



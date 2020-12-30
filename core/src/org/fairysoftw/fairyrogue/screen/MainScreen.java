package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.FairyRogue;
import org.fairysoftw.fairyrogue.actor.PlayerActor;
import org.fairysoftw.fairyrogue.stage.GameStage;

public class MainScreen extends ScreenAdapter {
    private GameStage stage;
    private Game game;
    private Array<TiledMap> maps;

    public MainScreen(Game game, Array<TiledMap> maps, PlayerActor lastPlayerActor) {
        this.game = game;
        this.maps = maps;
        this.stage = new GameStage(maps.get(0), lastPlayerActor);
        maps.removeIndex(0);
    }

    private void nextStage() {
        game.setScreen(new MainScreen(game, maps, this.stage.getPlayerActor()));
    }

    private void gameClear() {
        game.setScreen(new MenuScreen(game));
    }

    public void gameOver() {
        game.setScreen(new DeathScreen((FairyRogue) game));
    }

    @Override
    public void show() {
        stage.draw();
    }

    @Override
    public void render(float delta) {
        if (stage.isClear()) {
            nextStage();
            this.stage.dispose();
            return;
        } else if (stage.isGameOver()) {
            gameOver();
            return;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen((FairyRogue) game));
        }
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



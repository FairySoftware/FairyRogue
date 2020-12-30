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

/**
 * {@inheritDoc}
 * main screen that display the main game scene
 * will be change to next main screen every time clear a stage
 * will be change to setting screen when press esc
 * will be change to start screen when player died
 */
public class MainScreen extends ScreenAdapter {
    // current game stage
    private GameStage stage;
    // the major Game class
    private Game game;
    // the map array store next maps
    private Array<TiledMap> maps;

    /**
     * main screen constructor
     *
     * @param game            major game class instance
     * @param maps            the map array store next maps
     * @param lastPlayerActor last stage player
     */
    public MainScreen(Game game, Array<TiledMap> maps, PlayerActor lastPlayerActor) {
        this.game = game;
        this.maps = maps;
        this.stage = new GameStage(maps.get(0), lastPlayerActor);
        maps.removeIndex(0);
    }

    /**
     * set game to next main screen
     * will be call when this stage is clear
     */
    private void nextStage() {
        game.setScreen(new MainScreen(game, maps, this.stage.getPlayerActor()));
    }

    /**
     * clear game
     * will be call when player arrived clear point
     */
    private void gameClear() {
        game.setScreen(new WinScreen((FairyRogue) game));
    }

    /**
     * over the game
     * will be call when player died
     */
    public void gameOver() {
        game.setScreen(new DeathScreen((FairyRogue) game));
    }

    /**
     * {@inheritDoc}
     * will be called every this screen shows
     */
    @Override
    public void show() {
        stage.draw();
    }

    /**
     * render screen method
     * will be called every tick
     *
     * @param delta time interval between ticks
     */
    @Override
    public void render(float delta) {
        // if stage is clear and there are no map, the game is clear, show clear image
        if (stage.isClear() && maps.size == 0) {
            this.gameClear();
        }
        // if stage is clear, switch to next stage
        else if (stage.isClear()) {
            nextStage();
            this.stage.dispose();
            return;
        }
        // if game over, show game over image
        else if (stage.isGameOver()) {
            gameOver();
            return;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // call stage action
        stage.act();
        // call stage drawing
        stage.draw();
        // if esc is pressed, show setting screen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen((FairyRogue) game));
        }
    }

    /**
     * resize the window
     *
     * @param width  window width
     * @param height windows height
     */
    @Override
    public void resize(int width, int height) {
        stage.resize(width, height);
    }

    /**
     * dispose method, won't call automatically
     * please call manually
     */
    @Override
    public void dispose() {
        // dispose current game stage
        stage.dispose();
    }

}



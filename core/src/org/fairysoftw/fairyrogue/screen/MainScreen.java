package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.FairyRogue;
import org.fairysoftw.fairyrogue.actor.*;
import org.fairysoftw.fairyrogue.stage.GameStage;

public class MainScreen extends ScreenAdapter {
    private OrthogonalTiledMapRenderer mapRenderer;
    private Viewport currentViewport;
    private SpriteBatch batch;
    private GameStage stage;
    private PlayerActor playerActor;
    private OrthographicCamera mainCamera;

    public MainScreen(Game game) {
        mainCamera = new OrthographicCamera();
        currentViewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT, mainCamera);
        batch = new SpriteBatch();
        stage = new GameStage(currentViewport, batch);
        mapRenderer = new OrthogonalTiledMapRenderer(Assets.map, batch);
        mapRenderer.setView(mainCamera);

        for (MapObject object : Assets.map.getLayers().get("Objects").getObjects()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) object;
            Actor actor = null;
            if (object.getName().contains("wall")) {
                actor = new WallActor(mapObject.getTextureRegion());
            }
            else if (object.getName().contains("door")) {
                actor = new DoorActor(mapObject);
            }
            else if (object.getName().contains("player")) {
                playerActor = new PlayerActor(mapObject);
                actor = playerActor;
            }
            else if (object.getName().contains("monster")) {
                actor = new MonsterActor(mapObject);
            }
            else if (object.getName().contains("npc")) {
                actor = new NpcActor(mapObject.getTextureRegion());
            }
            else if (object.getName().contains("props")) {
                actor = new PropsActor(mapObject);
            }
            if (actor != null) {
                actor.setX(mapObject.getX());
                actor.setY(mapObject.getY());
                stage.addActor(actor);
            }
        }
    }

    @Override
    public void show() {
        stage.draw();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateCamera();
        mapRenderer.setView(mainCamera);
        mapRenderer.render();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height);
    }

    private void updateCamera() {
        mainCamera.position.x = playerActor.getX();
        mainCamera.position.y = playerActor.getY();

        mainCamera.update();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        stage.dispose();
    }

}



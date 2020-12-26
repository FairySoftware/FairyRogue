package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.FairyRogue;

public class MiniMapStage extends Stage {
    private MapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private NotUniqueGroup group;
    private Batch batch;

    public MiniMapStage(MapRenderer mapRenderer, Batch batch) {
        this.mapRenderer = mapRenderer;
        this.batch = batch;
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT, camera);
        this.group = new NotUniqueGroup();
        viewport.setScreenPosition(0,0);
        camera.zoom = 3;
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        viewport.setScreenPosition(-FairyRogue.VIRTUAL_WIDTH/3,-FairyRogue.VIRTUAL_HEIGHT/3);
    }

    @Override
    public void addActor(Actor actor) {
        this.group.addActor(actor);
    }

    @Override
    public void draw() {
        viewport.apply();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        group.draw(batch, 1);
        batch.end();
    }
}

class NotUniqueGroup extends Group {
    @Override
    public void addActor(Actor actor) {
        this.getChildren().add(actor);
    }
}

package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.FairyRogue;
import org.fairysoftw.fairyrogue.actor.CreatureActor;

public class AttributeStage extends Stage {
    private OrthographicCamera camera;
    private Viewport viewport;
    private Batch batch;
    private String playStr = " ";
    private String monsterStr = " ";

    public AttributeStage(Batch batch) {
        this.batch = batch;
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT, camera);
        viewport.setScreenPosition(0, 0);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public void setAttributeToStr(String playStr, String monsterStr) {
        this.playStr = "player attributes: \n" + playStr;
        if (monsterStr != "") {
            this.monsterStr = "monster attributes: \n" + monsterStr;
        } else {
            this.monsterStr = "";
        }
    }

    @Override
    public void draw() {
        viewport.apply();
        BitmapFont font = new BitmapFont();


        this.getBatch().begin();
        font.draw(this.getBatch(), playStr,
                this.getCamera().position.x - FairyRogue.VIRTUAL_WIDTH / 2f + 50,
                this.getCamera().position.y + FairyRogue.VIRTUAL_HEIGHT / 2f - 20);
        this.getBatch().end();
        this.getBatch().begin();
        font.setColor(Color.RED);
        font.draw(this.getBatch(), monsterStr,
                this.getCamera().position.x - FairyRogue.VIRTUAL_WIDTH / 2f + 50,
                this.getCamera().position.y + FairyRogue.VIRTUAL_HEIGHT / 2f - 220);
        this.getBatch().end();
    }
}

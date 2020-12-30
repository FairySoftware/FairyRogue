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

/**
 * AttributeStage will display player and monster's attributes information on the screen
 */
public class AttributeStage extends Stage {
    //Orthographic camera given to viewport
    private OrthographicCamera camera;
    //viewport of AttributeStage
    private Viewport viewport;
    //a batch to write font on the stage
    private Batch batch;
    //player's attribute information and needs to be displayed in this stage
    private String playStr = " ";
    //monster's attribute information and needs to be displayed in this stage
    private String monsterStr = " ";

    /**
     * Constructor
     * set stage's batch
     * @param batch
     */
    public AttributeStage(Batch batch) {
        this.batch = batch;
        this.camera = new OrthographicCamera();
        //set viewport ExtendViewport and set it at the center of the stage without zoom scale
        this.viewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT, camera);
        viewport.setScreenPosition(0, 0);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    /**
     * set playStr and monsterStr
     * @param playStr
     * @param monsterStr
     */
    public void setAttributeToStr(String playStr, String monsterStr) {
        this.playStr = "player attributes: \n" + playStr;
        if (monsterStr != "") {
            this.monsterStr = "monster attributes: \n" + monsterStr;
        } else {
            this.monsterStr = "";
        }
    }

    /**
     * override method draw()
     * display attribute information at the left of the screen
     */
    @Override
    public void draw() {
        viewport.apply();
        BitmapFont font = new BitmapFont();

        //use the batch of this screen to draw
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

package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.props.Props;
import org.fairysoftw.fairyrogue.ui.NpcDialog;

public class NpcActor extends CreatureActor {
    public String dialogue;
    public Props bonus;

    public NpcActor(TextureRegion region) {
        super(region);
    }

    public NpcActor(MapObject object, TiledMap tiledMap) {
        super(object);
        this.dialogue = (String) object.getProperties().get("dialogue");
        if (object.getProperties().containsKey("bonus")) {
            MapObjects toUseObjects = tiledMap.getLayers().get("Objects_to_use").getObjects();
            for (MapObject toUseObject : toUseObjects) {
                if((int)toUseObject.getProperties().get("id") == (int)object.getProperties().get("bonus")) {
                    this.bonus = Props.PropsFactory(toUseObject);
                }
            }
        }
    }

    public void takeDialogue(Stage stage, PlayerActor playerActor) {
        Dialog dialog = new NpcDialog("", Assets.skin, "dialog", this, playerActor);
        dialog.text(dialogue + "\n >Enter");
        dialog.key(Input.Keys.ENTER, true);
        dialog.show(stage);
        dialog.setPosition(stage.getCamera().position.x, stage.getCamera().position.y + 50);
        Gdx.input.setInputProcessor(stage);
    }
}

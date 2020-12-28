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
import org.fairysoftw.fairyrogue.stage.GameStage;
import org.fairysoftw.fairyrogue.ui.NpcDialog;
import org.json.JSONObject;

public class NpcActor extends CreatureActor {
    JSONObject dialogue;

    public NpcActor(TextureRegion region) {
        super(region);
    }

    public NpcActor(MapObject object, TiledMap tiledMap, JSONObject dialogueJson) {
        super(object);
        this.dialogue = dialogueJson;
    }

    public JSONObject getDialogue() {
        return dialogue;
    }

    public void takeDialogue(Stage stage, PlayerActor playerActor, JSONObject dialogueJson) {
        String content = dialogueJson.getString("content");
        MapObjects mapObjects = ((GameStage)this.getStage()).getCurrentMap().getLayers().get("Objects_to_use").getObjects();
        Props bonus = null;
        Array<JSONObject> choices = null;
        if(dialogueJson.has("bonus")) {
            for (MapObject mapObject : mapObjects) {
                if ((int) mapObject.getProperties().get("id") == dialogueJson.getInt("bonus")) {
                    bonus = Props.PropsFactory(mapObject);
                }
            }
        }
        if(dialogueJson.has("choices")) {
            choices = new Array<>();
            for(Object object:dialogueJson.getJSONArray("choices")) {
                JSONObject jsonObject = (JSONObject) object;
                choices.add(jsonObject);
            }
        }

        Dialog dialog = new NpcDialog("", Assets.skin, "dialog", this, playerActor, bonus, choices);
        dialog.text(content);
        dialog.show(stage);
        dialog.setPosition(stage.getCamera().position.x, stage.getCamera().position.y + 50);
        Gdx.input.setInputProcessor(stage);
    }
}

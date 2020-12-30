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

/**
 * npc actor will have communications with player actor
 * if player actor finish the mission, the npc will give player extra bonus
 */
public class NpcActor extends CreatureActor {
    //dialogue between npc and player
    JSONObject dialogue;

    /**
     * npc actor constructor
     * @param region
     */
    public NpcActor(TextureRegion region) {
        super(region);
    }

    /**
     * npc actor constructor
     * @param object
     * @param tiledMap
     * @param dialogueJson
     */
    public NpcActor(MapObject object, TiledMap tiledMap, JSONObject dialogueJson) {
        super(object);
        this.dialogue = dialogueJson;
    }

    /**
     * get dialogue
     * @return
     */
    public JSONObject getDialogue() {
        return dialogue;
    }

    /**
     * take dialogue between npc and player
     * @param stage
     * @param playerActor
     * @param dialogueJson
     */
    public void takeDialogue(Stage stage, PlayerActor playerActor, JSONObject dialogueJson) {
        //if the dialogue is not null, do the communication
        if(dialogueJson != null) {
            //load dialogue from game.json
            String content = dialogueJson.getString("content");
            MapObjects mapObjects = ((GameStage) this.getStage()).getCurrentMap().getLayers().get("Objects_to_use").getObjects();
            Props bonus = null;
            Array<JSONObject> choices = null;
            //if dialoguejson has bonus, set bonus and bonus will be given to the player
            if (dialogueJson.has("bonus")) {
                for (MapObject mapObject : mapObjects) {
                    if ((int) mapObject.getProperties().get("id") == dialogueJson.getInt("bonus")) {
                        bonus = Props.PropsFactory(mapObject);
                    }
                }
            }
            //if dialoguejson have choices, out put the boxes for user to check their choices
            if (dialogueJson.has("choices")) {
                choices = new Array<>();
                for (Object object : dialogueJson.getJSONArray("choices")) {
                    JSONObject jsonObject = (JSONObject) object;
                    choices.add(jsonObject);
                }
            }

            Dialog dialog = new NpcDialog("", Assets.skin, "dialog", this, playerActor, bonus, choices);
            dialog.text(content);
            dialog.show(stage);
            dialog.setPosition(stage.getCamera().position.x-GameStage.MAIN_LOCATION_X, stage.getCamera().position.y + 50);
            Gdx.input.setInputProcessor(stage);
        }
        else {
            playerActor.overDialogue();
        }
    }
}

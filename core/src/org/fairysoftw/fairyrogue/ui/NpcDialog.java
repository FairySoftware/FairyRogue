package org.fairysoftw.fairyrogue.ui;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.actor.NpcActor;
import org.fairysoftw.fairyrogue.actor.PlayerActor;
import org.fairysoftw.fairyrogue.props.Props;
import org.json.JSONObject;


public class NpcDialog extends Dialog {
    private PlayerActor playerActor;
    public NpcActor npcActor;
    private Props bonus;
    private Array<JSONObject> choices;
    public NpcDialog(String title, Skin skin, String windowStyleName, NpcActor npc, PlayerActor player, Props bonus, Array<JSONObject> choices) {
        super(title, skin, windowStyleName);
        this.playerActor = player;
        this.npcActor = npc;
        this.bonus = bonus;
        if(choices != null) {
            for (JSONObject choice : choices) {
                this.button(choice.getString("content"), choice.getJSONObject("dialogue"));
            }
        }
        else {
            this.key(Input.Keys.ENTER, null);
        }
    }

    @Override
    protected void result(Object object) {
        playerActor.overDialogue();
        if(object != null) playerActor.takeDialogue(npcActor, (JSONObject) object);
        if(bonus != null)
        {
            playerActor.pickUp(bonus);
        }
        this.remove();
    }
}

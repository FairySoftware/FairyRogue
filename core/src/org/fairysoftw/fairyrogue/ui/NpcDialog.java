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


/** {@inheritDoc}
 * This dialog is use to display the npc dialogue with character.
 */
public class NpcDialog extends Dialog {
    // the player that initiate the dialogue
    private PlayerActor playerActor;
    // the npc that player talk with
    public NpcActor npcActor;
    // the bonus may player receive when dialogue over
    private Props bonus;
    // the choices that player can choose
    private Array<JSONObject> choices;

    /**
     *
     * @param title title of the dialogue
     * @param skin skin that dialogue apply
     * @param windowStyleName windows style name that dialogue have
     * @param npc npc that player talk with
     * @param player the player that initiate the dialogue
     * @param bonus the bonus may player receive when dialogue over
     * @param choices the choices that player can choose
     */
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

    /**
     * {@inheritDoc}
     * override the key press result
     * @param object
     */
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

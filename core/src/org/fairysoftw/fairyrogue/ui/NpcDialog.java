package org.fairysoftw.fairyrogue.ui;


import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.fairysoftw.fairyrogue.actor.NpcActor;
import org.fairysoftw.fairyrogue.actor.PlayerActor;

public class NpcDialog extends Dialog {
    private PlayerActor playerActor;
    public NpcActor npcActor;
    public NpcDialog(String title, Skin skin, String windowStyleName, NpcActor npc, PlayerActor player) {
        super(title, skin, windowStyleName);
        this.playerActor = player;
        this.npcActor = npc;
    }

    @Override
    protected void result(Object object) {
        playerActor.overDialogue();
        if(npcActor.bonus != null)
        {
            playerActor.pickUp(npcActor.bonus);
            npcActor.bonus = null;
        }
        this.remove();
    }
}

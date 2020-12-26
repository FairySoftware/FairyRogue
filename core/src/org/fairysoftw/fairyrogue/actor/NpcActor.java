package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class NpcActor extends CreatureActor {
    public NpcActor(TextureRegion region) {
        super(region);
    }

    public NpcActor(MapObject object) {
        super(object);
    }
    //TODO: finish npc action
}

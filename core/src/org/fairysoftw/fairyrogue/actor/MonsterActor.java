
package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class MonsterActor extends CreatureActor {
    boolean irritated;
    public MonsterActor(TextureRegion region) {
        super(region);
    }

    public MonsterActor(MapObject mapObject) {
        super(mapObject);
    }
    //TODO: finish monster action
}


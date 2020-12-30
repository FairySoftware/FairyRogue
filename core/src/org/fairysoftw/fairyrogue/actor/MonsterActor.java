package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

/**
 * monstor actor will take battle with player actor
 */
public class MonsterActor extends CreatureActor {
    boolean irritated;

    /**
     * monstor actor constructor
     * @param region
     */
    public MonsterActor(TextureRegion region) {
        super(region);
    }

    /**
     * monstor actor constructor
     * @param mapObject
     */
    public MonsterActor(MapObject mapObject) {
        super(mapObject);
    }
}

package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

/**
 * actor mainly contains the walls objects from map and has its own action
 */
public class WallActor extends SpriteActor {
    /**
     * wall actor constructor
     * @param region
     */
    public WallActor(TextureRegion region) {
        super(region);
    }

    /**
     * wall actor constructor
     * @param mapObject
     */
    public WallActor(MapObject mapObject) {
        super(mapObject);
    }
}

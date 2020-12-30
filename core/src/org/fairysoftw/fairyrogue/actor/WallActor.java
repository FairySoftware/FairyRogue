package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

/**
 * WallActor mainly contains the walls objects from map
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

package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class WallActor extends SpriteActor {
    public WallActor(TextureRegion region) {
        super(region);
    }

    public WallActor(MapObject mapObject) {
        super(mapObject);
    }
}

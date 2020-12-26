package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class DoorActor extends SpriteActor {

    private boolean locked = false;

    public DoorActor(TextureRegion region) {
        super(region);
    }

    public DoorActor(MapObject object) {
        super(object);
        locked = (boolean) object.getProperties().get("locked");
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean unLock() {
        locked = false;
        return true;
    }

    //TODO: finish door open & close animation
}

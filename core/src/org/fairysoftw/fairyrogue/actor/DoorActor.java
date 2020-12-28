package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import org.fairysoftw.fairyrogue.Assets;

public class DoorActor extends SpriteActor {

    public String id = "";
    private boolean locked = false;
    private boolean closed = false;
    public TextureRegion lockedDoor = null;
    public TextureRegion closedDoor = null;
    public TextureRegion openedDoor = null;
    String material;

    public DoorActor(TextureRegion region) {
        super(region);
    }

    public DoorActor(MapObject object) {
        super(object);
        locked = (boolean) object.getProperties().get("locked");
        closed = (boolean) object.getProperties().get("closed");
        material = (String) object.getProperties().get("material");
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean open() {
        if (!this.locked) {
            this.closed = false;
            this.sprite.setRegion(openedDoor);
            return true;
        }
        else {
            return false;
        }
    }

    public void close() {
        if (!this.locked) {
            this.closed = true;
            this.sprite.setRegion(closedDoor);
        }

    }

    public void unlock() {
        locked = false;
        this.sprite.setRegion(closedDoor);
    }

    public boolean unlock(String id) {
        if (this.id.equals(id)) {
            unlock();
            return true;
        }
        else {
            return false;
        }
    }

    public String getMaterial() {
        return material;
    }

    public void refresh() {
        if (this.locked) {
            this.sprite.setRegion(lockedDoor);
        }
        else if (this.closed) {
            this.sprite.setRegion(closedDoor);
        }
        else {
            this.sprite.setRegion(openedDoor);
        }
    }
    //TODO: finish door open & close animation
}

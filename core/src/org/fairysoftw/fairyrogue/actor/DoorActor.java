package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import org.fairysoftw.fairyrogue.Assets;

public class DoorActor extends SpriteActor {

    private String id = "";
    private boolean locked = false;
    private boolean closed = false;
    private TextureRegion lockedDoor = null;
    private TextureRegion closedDoor = null;
    private TextureRegion openedDoor = null;

    public DoorActor(TextureRegion region) {
        super(region);
    }

    public DoorActor(MapObject object) {
        super(object);
        locked = (boolean) object.getProperties().get("locked");
        closed = (boolean) object.getProperties().get("closed");
        String material = (String) object.getProperties().get("material");
        switch (material) {
            case "wood":
                closedDoor = Assets.closedWoodDoor;
                openedDoor = Assets.openedWoodDoor;
                break;
            case "iron":
                lockedDoor = Assets.lockedIronDoor;
                closedDoor = Assets.closedIronDoor;
                openedDoor = Assets.openedIronDoor;
                id = (String) object.getProperties().get("id");
                break;
        }
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

    //TODO: finish door open & close animation
}

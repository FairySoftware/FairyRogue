package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import org.fairysoftw.fairyrogue.Assets;

/**
 * actor contains door tiledmap object from map and has its own action
 */
public class DoorActor extends SpriteActor {
    //door's id, can be matched with the key's id
    public String id = "";
    //if the door is locked
    private boolean locked = false;
    //if the door is closed
    private boolean closed = false;
    //textureRegion to load the locked door image
    public TextureRegion lockedDoor = null;
    //textureRegion to load the closed door image
    public TextureRegion closedDoor = null;
    //textureRegion to load the opened door image
    public TextureRegion openedDoor = null;
    //each door has material. "iron" needs key to open and "wood" can be opened easily
    String material;

    /**
     * door actor constructor
     * @param region
     */
    public DoorActor(TextureRegion region) {
        super(region);
    }

    /**
     * door actor constructor
     * @param object
     */
    public DoorActor(MapObject object) {
        super(object);
        locked = (boolean) object.getProperties().get("locked");
        closed = (boolean) object.getProperties().get("closed");
        material = (String) object.getProperties().get("material");
    }

    /**
     * if the door is locked
     * @return
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * if the door is closed
     * @return boolean
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * open the door
     * change the door actors' texture to openedDoor texture
     * @return boolean
     */
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

    /**
     * close the door
     * change the door actors' texture to closedDoor texture
     */
    public void close() {
        if (!this.locked) {
            this.closed = true;
            this.sprite.setRegion(closedDoor);
        }

    }

    /**
     * unlock the door
     * change the door actors' texture to openedDoor texture
     */
    public void unlock() {
        locked = false;
        this.sprite.setRegion(closedDoor);
    }

    /**
     * unlock the door
     * if the key's id match with door's id unlock the door
     * @param id
     * @return
     */
    public boolean unlock(String id) {
        if (this.id.equals(id)) {
            unlock();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * get the door's material
     * @return
     */
    public String getMaterial() {
        return material;
    }

    /**
     * refesh the textureRegion
     */
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
}

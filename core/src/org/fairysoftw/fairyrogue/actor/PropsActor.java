package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

/**
 * actor contains props tiledmap object from map and has its own action
 */
public class PropsActor extends SpriteActor {
    //map object
    protected MapObject mapObject = null;

    //there kinds of props and each of them have different function
    public enum PropsType {
        //equipment can be wore be player and make player stronger
        EQUIPMENT,
        //key to open the door
        KEY,
        //potion that can restore player's health and magic
        POTION
    }

    /**
     * props actor constructor
     *
     * @param region
     */
    public PropsActor(TextureRegion region) {
        super(region);
    }

    /**
     * props actor constructor
     *
     * @param mapObject
     */
    public PropsActor(MapObject mapObject) {
        super(mapObject);
        this.mapObject = mapObject;
    }

    /**
     * get props type
     *
     * @return PropsType
     */
    public PropsType getPropsType() {
        if (mapObject == null) {
            return null;
        }
        String type = (String) mapObject.getProperties().get("type");
        if (type.contains("equipment")) {
            return PropsType.EQUIPMENT;
        } else if (type.contains("key")) {
            return PropsType.KEY;
        } else if (type.contains("potion")) {
            return PropsType.POTION;
        }
        return null;
    }

    /**
     * get props name from mapObject's properties
     *
     * @return String
     */
    public String getPropsName() {
        return (String) mapObject.getProperties().get("props_name");
    }

    /**
     * get texture from mapObject
     *
     * @return Texture
     */
    public Texture getTexture() {
        return ((TiledMapTileMapObject) mapObject).getTextureRegion().getTexture();
    }

    /**
     * get properties of mapObject
     *
     * @return MapProperties
     */
    public MapProperties getProperties() {
        if (mapObject != null) {
            return mapObject.getProperties();
        } else {
            return null;
        }
    }

    /**
     * get mapObject of this actor
     *
     * @return MapObject
     */
    public MapObject getMapObject() {
        if (mapObject != null) {
            return mapObject;
        } else {
            return null;
        }
    }
}

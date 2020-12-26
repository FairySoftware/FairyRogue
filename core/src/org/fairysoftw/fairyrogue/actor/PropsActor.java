package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

public class PropsActor extends SpriteActor {
    protected MapObject mapObject = null;

    public enum PropsType {
        EQUIPMENT,
        KEY,
        POTION
    }

    public PropsActor(TextureRegion region) {
        super(region);
    }

    public PropsActor(MapObject mapObject) {
        super(mapObject);
        this.mapObject = mapObject;
    }

    public PropsType getPropsType() {
        if (mapObject == null) {
            return null;
        }
        String type = (String) mapObject.getProperties().get("type");
        if (type.contains("weapon")) {
            return PropsType.EQUIPMENT;
        }
        else if (type.contains("key")) {
            return PropsType.KEY;
        }
        else if (type.contains("potion")) {
            return PropsType.POTION;
        }
        return null;
    }

    public String getPropsName() {
        return (String) mapObject.getProperties().get("props_name");
    }

    public Texture getTexture() {
        return ((TiledMapTileMapObject) mapObject).getTextureRegion().getTexture();
    }

    public MapProperties getProperties() {
        if (mapObject != null) {
            return mapObject.getProperties();
        }
        else {
            return null;
        }
    }
}

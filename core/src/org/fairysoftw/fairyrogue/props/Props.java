package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import org.fairysoftw.fairyrogue.actor.PropsActor;

public class Props {
    public Sprite icon;
    public String name;
    public PropsActor.PropsType propsType;

    public Props() {
    }

    public Props(MapObject mapObject) {
        this.icon = new Sprite(((TiledMapTileMapObject) mapObject).getTextureRegion());
        this.name = mapObject.getName();
        String type = (String) mapObject.getProperties().get("type");
        if (type.contains("equipment")) {
            this.propsType = PropsActor.PropsType.EQUIPMENT;
        }
        else if (type.contains("key")) {
            this.propsType = PropsActor.PropsType.KEY;
        }
        else if (type.contains("potion")) {
            this.propsType = PropsActor.PropsType.POTION;
        }
    }

    public static Props PropsFactory(MapObject mapObject) {
        String type = (String) mapObject.getProperties().get("type");
        if (type.contains("equipment")) {
            return new Equipment(mapObject);
        }
        else if (type.contains("key")) {
            return new Key(mapObject);
        }
        else if (type.contains("potion")) {
            return new Potion(mapObject);
        }
        return null;
    }
}

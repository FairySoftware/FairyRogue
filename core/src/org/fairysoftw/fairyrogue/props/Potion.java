package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapObject;

public class Potion extends Props {
    public float healthConsume = 0;
    public float magicConsume = 0;
    public Potion(MapObject mapObject) {
        super(mapObject);
        healthConsume = (float) mapObject.getProperties().get("healthConsume");
        magicConsume = (float) mapObject.getProperties().get("magicConsume");
    }
}

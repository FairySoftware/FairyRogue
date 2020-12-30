package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapObject;

/** {@inheritDoc}
 * potion class inherit props
 */
public class Potion extends Props {
    public float healthConsume = 0;
    public float magicConsume = 0;

    /**
     * constructor of potion
     * @param mapObject map object of potion
     */
    public Potion(MapObject mapObject) {
        super(mapObject);
        //set potion properties
        healthConsume = (float) mapObject.getProperties().get("healthConsume");
        magicConsume = (float) mapObject.getProperties().get("magicConsume");
    }
}

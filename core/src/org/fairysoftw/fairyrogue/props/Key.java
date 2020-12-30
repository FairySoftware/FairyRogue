package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

/** {@inheritDoc}
 * key class inherit props
 */
public class Key extends Props {
    private final String id;

    /**
     * constructor of key
     * @param properties properties of key
     */
    public Key(MapProperties properties) {
        id = (String) properties.get("id");
    }

    /**
     * constructor of key
     * @param mapObject map object of key
     */
    public Key(MapObject mapObject) {
        id = (String) mapObject.getProperties().get("id");
    }

    /**
     * get id of key
     * @return return id
     */
    public String getId() {
        return id;
    }
}

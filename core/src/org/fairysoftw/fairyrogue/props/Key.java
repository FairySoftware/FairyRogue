package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapProperties;

public class Key extends Props {
    private final String id;

    public Key(MapProperties properties) {
        id = (String) properties.get("id");
    }

    public String getId() {
        return id;
    }
}

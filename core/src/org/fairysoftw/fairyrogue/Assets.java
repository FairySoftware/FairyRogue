package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
    private static final AssetManager manager = new AssetManager();

    public static TiledMap map = null;
    private static final AssetDescriptor<Map> mapDescriptor =
            new AssetDescriptor<>("tiledmap/map_0.tmx", Map.class);

    public static void load()
    {
        map = new TmxMapLoader().load("tiledmap/map_0.tmx");
        manager.finishLoading();
    }

    public static void dispose()
    {
        manager.dispose();
    }
}
package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

public class Assets {
    private static final AssetManager manager = new AssetManager();

    public static TiledMap map = null;
    public static TextureRegion lockedIronDoor = null;
    public static TextureRegion closedIronDoor = null;
    public static TextureRegion openedIronDoor = null;
    public static TextureRegion closedWoodDoor = null;
    public static TextureRegion openedWoodDoor = null;
    private static AssetDescriptor<Map> mapDescriptor =
            new AssetDescriptor<>("tiledmap/map_0.tmx", Map.class);

    public static void load() {
        map = new TmxMapLoader().load("tiledmap/map_0.tmx");
        MapObjects mapObjects = map.getLayers().get("Objects_to_use").getObjects();
        for (MapObject mapObject : mapObjects) {
            TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) mapObject;
            if (tileMapObject.getName().contains("door")) {
                MapProperties mapProperties = tileMapObject.getProperties();
                TextureRegion textureRegion = tileMapObject.getTextureRegion();
                if (mapProperties.get("material").equals("iron")) {
                    switch ((String) mapProperties.get("status")) {
                        case "locked":
                            lockedIronDoor = textureRegion;
                            break;
                        case "closed":
                            closedIronDoor = textureRegion;
                            break;
                        case "opened":
                            openedIronDoor = textureRegion;
                            break;
                    }
                }
                if (mapProperties.get("material").equals("wood")) {
                    switch ((String) mapProperties.get("status")) {
                        case "closed":
                            closedWoodDoor = textureRegion;
                            break;
                        case "opened":
                            openedWoodDoor = textureRegion;
                            break;
                    }
                }
            }
        }
        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }
}
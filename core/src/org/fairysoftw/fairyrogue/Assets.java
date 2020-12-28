package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private static final AssetManager manager = new AssetManager();

    public static TiledMap map = null;
    public static Skin skin = null;
    public static TextureRegion lockedIronDoor = null;
    public static TextureRegion closedIronDoor = null;
    public static TextureRegion openedIronDoor = null;
    public static TextureRegion closedWoodDoor = null;
    public static TextureRegion openedWoodDoor = null;

    public static Music bgm;

    public static Sound attackSound;
    public static Sound doorSound;
    public static Sound unlockSound;
    public static Sound pickSound;
    public static Sound equipSound;
    public static Sound deathSound;

    private static AssetDescriptor<Map> mapDescriptor =
            new AssetDescriptor<>("tilemap/map_0.tmx", Map.class);

    public static void load() {
        map = new TmxMapLoader().load("tilemap/map_0.tmx");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
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

        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm.mp3"));
        bgm.setLooping(true);
        bgm.play();

        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/attack.mp3"));
        doorSound = Gdx.audio.newSound(Gdx.files.internal("sounds/door.mp3"));
        unlockSound = Gdx.audio.newSound(Gdx.files.internal("sounds/floor.mp3"));
        pickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/item.mp3"));
        equipSound = Gdx.audio.newSound(Gdx.files.internal("sounds/equip.mp3"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/zone.mp3"));

        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }
}
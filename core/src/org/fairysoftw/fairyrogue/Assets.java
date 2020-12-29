package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private static final AssetManager manager = new AssetManager();

    public static Skin skin = null;
    public static Music bgm;
    public static Sound attackSound;
    public static Sound doorSound;
    public static Sound unlockSound;
    public static Sound pickSound;
    public static Sound equipSound;
    public static Sound deathSound;
    private static boolean openSound;

    public static void load() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

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

    public static boolean isOpenSound() {
        return openSound;
    }

    public static void openSound() {
        openSound = true;
    }

    public static void closeSound() {
        openSound = false;
    }
}
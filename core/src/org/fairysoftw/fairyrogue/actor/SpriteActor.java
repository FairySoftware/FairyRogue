package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class SpriteActor extends Actor {
    protected Sprite sprite;

    public SpriteActor(TextureRegion region) {
        sprite = new Sprite(region);
        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);
    }

    public SpriteActor(MapObject mapObject) {
        this(((TiledMapTileMapObject) mapObject).getTextureRegion());
        this.setName(mapObject.getName());
    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getY() {
        return sprite.getY();
    }

    @Override
    public void setX(float x) {
        sprite.setX(x);
    }

    @Override
    public void setY(float y) {
        sprite.setY(y);
    }

    @Override
    public float getWidth() {
        return sprite.getWidth();
    }

    @Override
    public float getHeight() {
        return sprite.getHeight();
    }
}

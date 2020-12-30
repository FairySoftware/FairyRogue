package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import org.fairysoftw.fairyrogue.stage.MiniMapStage;

/**
 * used
 */
public class SpriteActor extends Actor {
    //sprite is used to set map textureRegion and set location and size
    protected Sprite sprite;
    //minimapStage: actor can be added to minimap stage and display in minimap
    public MiniMapStage miniMapStage = null;

    /**
     * constructor
     * initialize sprite and set its position and attributes
     *
     * @param region
     */
    public SpriteActor(TextureRegion region) {
        sprite = new Sprite(region);
        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);
    }

    /**
     * constructor
     * initialize sprite and set its position and attributes
     *
     * @param mapObject
     */
    public SpriteActor(MapObject mapObject) {
        this(((TiledMapTileMapObject) mapObject).getTextureRegion());
        this.setName(mapObject.getName());
    }

    /**
     * set sprite position and bounds
     *
     * @param x
     * @param y
     */
    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * remove actor
     *
     * @return
     */
    @Override
    public boolean remove() {
        Group parent = this.getParent();
        Stage stage = this.getStage();
        if (miniMapStage != null) {
            miniMapStage.remove(this, true);
        }
        this.setParent(parent);
        this.setStage(stage);
        return super.remove();
    }

    /**
     * draw image on screen
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    /**
     * get x-coordinate of sprite
     *
     * @return
     */
    @Override
    public float getX() {
        return sprite.getX();
    }

    /**
     * get y-coordinate of sprite
     *
     * @return
     */
    @Override
    public float getY() {
        return sprite.getY();
    }

    /**
     * set x-coordinate of sprite
     *
     * @param x
     */
    @Override
    public void setX(float x) {
        sprite.setX(x);
    }

    /**
     * set y-coordinate of sprite
     *
     * @param y
     */
    @Override
    public void setY(float y) {
        sprite.setY(y);
    }

    /**
     * git width of the sprite(TextRegion)
     *
     * @return
     */
    @Override
    public float getWidth() {
        return sprite.getWidth();
    }

    /**
     * git height of the sprite(TextRegion)
     *
     * @return
     */
    @Override
    public float getHeight() {
        return sprite.getHeight();
    }
}

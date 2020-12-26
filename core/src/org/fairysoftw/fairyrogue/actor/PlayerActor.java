package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerActor extends SpriteActor {
    private Vector2 lastPosition;

    public PlayerActor(TextureRegion region) {
        super(region);
    }

    @Override
    public void act(float delta) {
        lastPosition = new Vector2(this.getX(), this.getY());
        if (Gdx.input.isKeyJustPressed(Input.Keys.K) ||
                Gdx.input.isKeyJustPressed(Input.Keys.W) ||
                Gdx.input.isKeyJustPressed(Input.Keys.UP))//up
        {
            sprite.setY(sprite.getY() + 32);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.J) ||
                Gdx.input.isKeyJustPressed(Input.Keys.S) ||
                Gdx.input.isKeyJustPressed(Input.Keys.DOWN))//down
        {
            sprite.setY(sprite.getY() - 32);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.H) ||
                Gdx.input.isKeyJustPressed(Input.Keys.A) ||
                Gdx.input.isKeyJustPressed(Input.Keys.LEFT))//left
        {
            sprite.setX(sprite.getX() - 32);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.L) ||
                Gdx.input.isKeyJustPressed(Input.Keys.D) ||
                Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))//right
        {
            sprite.setX(sprite.getX() + 32);
        }
    }

    public void undoAct() {
        this.setX(lastPosition.x);
        this.setY(lastPosition.y);
    }
}

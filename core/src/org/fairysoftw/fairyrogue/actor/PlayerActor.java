package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.props.Equipment;
import org.fairysoftw.fairyrogue.props.Props;

import static com.badlogic.gdx.utils.JsonValue.ValueType.object;

public class PlayerActor extends CreatureActor {
    private Vector2 lastPosition;
    public Array<Props> backpack = new Array<>();
    private final int backpackCapacity = 100;
    private Equipment weapon = null;
    private Equipment apparel_head = null;
    private Equipment apparel_upper_body = null;
    private Equipment apparel_lower_body = null;
    private Equipment accessories = null;

    public PlayerActor(TextureRegion region) {
        super(region);
    }

    public PlayerActor(MapObject mapObject) {
        super(mapObject);
    }

    @Override
    public void act(float delta) {
        lastPosition = new Vector2(this.getX(), this.getY());
        if (Gdx.input.isKeyJustPressed(Input.Keys.K) ||
                Gdx.input.isKeyJustPressed(Input.Keys.W) ||
                Gdx.input.isKeyJustPressed(Input.Keys.UP))//up
        {
            sprite.setY(sprite.getY() + 32);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.J) ||
                Gdx.input.isKeyJustPressed(Input.Keys.S) ||
                Gdx.input.isKeyJustPressed(Input.Keys.DOWN))//down
        {
            sprite.setY(sprite.getY() - 32);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.H) ||
                Gdx.input.isKeyJustPressed(Input.Keys.A) ||
                Gdx.input.isKeyJustPressed(Input.Keys.LEFT))//left
        {
            sprite.setX(sprite.getX() - 32);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.L) ||
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

    public void pickUp(Props props) {
        if (backpack.size < backpackCapacity) {
            backpack.add(props);
        }
    }

    public void dropDown(Props props) {
        backpack.removeValue(props, false);
    }

    public void equip(Equipment equipment) {
        switch (equipment.type) {
            case WEAPON:
                weapon = equipment;
                break;
            case APPAREL_HEAD:
                apparel_head = equipment;
                break;
            case APPAREL_UPPER_BODY:
                apparel_upper_body = equipment;
                break;
            case APPAREL_LOWER_BODY:
                apparel_lower_body = equipment;
                break;
            case ACCESSORIES:
                accessories = equipment;
                break;
            default:
                break;
        }
        updateAttributes();
    }

    private void updateAttributes() {
        this.attackDamage = weapon.attackDamage;
        this.abilityPower = weapon.abilityPower;
        this.physicalDefence = apparel_head.physicalDefence +
                apparel_upper_body.physicalDefence +
                apparel_lower_body.physicalDefence +
                accessories.physicalDefence;
        this.magicalDefence = apparel_head.magicalDefence +
                apparel_upper_body.magicalDefence +
                apparel_lower_body.magicalDefence +
                accessories.magicalDefence;
    }
}

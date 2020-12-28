package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Array;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.props.Equipment;
import org.fairysoftw.fairyrogue.props.Potion;
import org.fairysoftw.fairyrogue.props.Props;
import org.fairysoftw.fairyrogue.screen.MainScreen;

public class PlayerActor extends CreatureActor {
    private Vector2 lastPosition = new Vector2();
    public Array<Props> backpack = new Array<>();
    private final int backpackCapacity = 100;
    private Equipment weapon = new Equipment(Equipment.EquipmentType.NONE);
    private Equipment apparel_head = new Equipment(Equipment.EquipmentType.NONE);
    private Equipment apparel_upper_body = new Equipment(Equipment.EquipmentType.NONE);
    private Equipment apparel_lower_body = new Equipment(Equipment.EquipmentType.NONE);
    private Equipment accessories = new Equipment(Equipment.EquipmentType.NONE);
    private boolean inDialogue = false;

    public PlayerActor(TextureRegion region) {
        super(region);
    }

    public PlayerActor(MapObject mapObject) {
        super(mapObject);
        weapon.attackDamage = this.attackDamage;
        weapon.abilityPower= this.abilityPower;
        weapon.attackSpeed = this.attackSpeed;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        lastPosition.x = this.getX();
        lastPosition.y = this.getY();
        if (!inBattle && !inDialogue) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.K) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.UP))//up
            {
                sprite.setY(sprite.getY() + 32);
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.J) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.DOWN))//down
            {
                sprite.setY(sprite.getY() - 32);
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.H) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.LEFT))//left
            {
                sprite.setX(sprite.getX() - 32);
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.L) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))//right
            {
                sprite.setX(sprite.getX() + 32);
            }
        }
    }

    @Override
    public void takeDeath() {
        Assets.deathSound.play();
        Dialog dialog = new Dialog("", Assets.skin, "dialog"){
            @Override
            protected void result(Object object) {
                reborn();
            }
        };
        dialog.key(Input.Keys.ENTER, true);
        dialog.text("you have die!!!" + "\n >Enter");
        dialog.key(Input.Keys.ENTER, true);
        dialog.show(this.getStage());
        dialog.setPosition(this.getStage().getCamera().position.x, this.getStage().getCamera().position.y + 50);
        Gdx.input.setInputProcessor(this.getStage());
    }

    public void reborn() {
        Gdx.app.exit();
    }

    public void undoAct() {
        this.setX(lastPosition.x);
        this.setY(lastPosition.y);
    }

    public void pickUp(Props props) {
        if (backpack.size < backpackCapacity) {
            backpack.add(props);
        }

        Assets.pickSound.play();

        if (props.propsType == PropsActor.PropsType.EQUIPMENT) {
            Equipment equipment = (Equipment) props;
            switch (equipment.equipmentType) {
                case WEAPON:
                    if (this.weapon.equipmentType == Equipment.EquipmentType.NONE) {
                        this.weapon = equipment;
                    }
                    break;
                case APPAREL_HEAD:
                    if (this.apparel_head.equipmentType == Equipment.EquipmentType.NONE) {
                        this.apparel_head = equipment;
                    }
                    break;
                case APPAREL_UPPER_BODY:
                    if (this.apparel_upper_body.equipmentType == Equipment.EquipmentType.NONE) {
                        this.apparel_upper_body = equipment;
                    }
                    break;
                case APPAREL_LOWER_BODY:
                    if (this.apparel_lower_body.equipmentType == Equipment.EquipmentType.NONE) {
                        this.apparel_lower_body = equipment;
                    }
                    break;
                case ACCESSORIES:
                    if (this.accessories.equipmentType == Equipment.EquipmentType.NONE) {
                        this.accessories = equipment;
                    }
                    break;
            }
            updateAttributes();
        }
        else if (props.propsType == PropsActor.PropsType.POTION) {
            this.usePotion((Potion) props);
        }
    }

    public void usePotion(Potion potion) {
        this.healthPoint -= potion.healthConsume;
        this.magicPoint -= potion.magicConsume;
    }

    public void dropDown(Props props) {
        backpack.removeValue(props, false);
    }

    public void equip(Equipment equipment) {

        Assets.equipSound.play();

        switch (equipment.equipmentType) {
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

    public void takeDialogue(NpcActor actor) {
        if(!inDialogue) {
            this.inDialogue = true;
            actor.takeDialogue(this.getStage(), this);
        }
    }

    public boolean inDialogue() {
        return inDialogue;
    }

    public void overDialogue() {
        this.inDialogue = false;
    }

    private void updateAttributes() {
        this.attackSpeed = weapon.attackSpeed;
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

package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.props.Equipment;
import org.fairysoftw.fairyrogue.props.Potion;
import org.fairysoftw.fairyrogue.props.Props;
import org.fairysoftw.fairyrogue.screen.MainScreen;
import org.json.JSONObject;

/** {@inheritDoc}
 * actor that inherit creature actor
 * represent player actor
 */
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

    /**
     * constructor of PlayerActor
     * @param region texture region
     */
    public PlayerActor(TextureRegion region) {
        super(region);
    }

    /**
     * constructor of PlayerActor
     * @param mapObject map object of player
     */
    public PlayerActor(MapObject mapObject) {
        super(mapObject);
        weapon.attackDamage = this.attackDamage;
        weapon.abilityPower = this.abilityPower;
        weapon.attackSpeed = this.attackSpeed;
    }

    /**
     * act method of player actor
     * @param delta time interval between ticks
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        lastPosition.x = this.getX();
        lastPosition.y = this.getY();
        // move method of actor
        if (!inBattle && !inDialogue) {
            // move up
            if (Gdx.input.isKeyJustPressed(Input.Keys.K) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.UP))//up
            {
                sprite.setY(((int) sprite.getY()) + 32);
            }
            // move down
            else if (Gdx.input.isKeyJustPressed(Input.Keys.J) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.DOWN))//down
            {
                sprite.setY(((int) sprite.getY()) - 32);
            }
            // move left
            else if (Gdx.input.isKeyJustPressed(Input.Keys.H) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.LEFT))//left
            {
                sprite.setX(((int) sprite.getX()) - 32);
            }
            // move right
            else if (Gdx.input.isKeyJustPressed(Input.Keys.L) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))//right
            {
                sprite.setX(((int) sprite.getX()) + 32);
            }
        }
    }

    /**
     * death action of player
     */
    @Override
    public void takeDeath() {
        if (Assets.isOpenSound())
            Assets.deathSound.play();
    }

    /**
     * undo player move action
     */
    public void undoAct() {
        this.setX(lastPosition.x);
        this.setY(lastPosition.y);
    }

    /**
     * pick up props method
     * @param props
     */
    public void pickUp(Props props) {
        // if sound option is open, play pick up sound
        if (Assets.isOpenSound())
            Assets.pickSound.play();

        // if props type is equipment, equip it
        if (props.propsType == PropsActor.PropsType.EQUIPMENT) {
            Equipment equipment = (Equipment) props;
            switch (equipment.equipmentType) {
                case WEAPON:
                    this.weapon = equipment;
                    break;
                case APPAREL_HEAD:
                    this.apparel_head = equipment;
                    break;
                case APPAREL_UPPER_BODY:
                    this.apparel_upper_body = equipment;
                    break;
                case APPAREL_LOWER_BODY:
                    this.apparel_lower_body = equipment;
                    break;
                case ACCESSORIES:
                    this.accessories = equipment;
                    break;
            }
            updateAttributes();
        }
        // if props is potion, use it
        else if (props.propsType == PropsActor.PropsType.POTION) {
            this.usePotion((Potion) props);
        }
        // if props is key, put it into backpack
        else if (props.propsType == PropsActor.PropsType.KEY) {
            this.backpack.add(props);
        }
    }

    /**
     * use potion to recover health point and magic point
     * @param potion potion to use
     */
    public void usePotion(Potion potion) {
        this.healthPoint -= potion.healthConsume;
        this.magicPoint -= potion.magicConsume;
    }

    /**
     * take attack method of player
     * attack speed base on player attack speed
     */
    @Override
    public void takeAttack() {
        long now = TimeUtils.nanoTime();
        // if there are not time to attack, player attack animation
        if (now - lastAttack > 1000000000 / attackSpeed && !this.isDead() && opponent != null) {
            this.lastAttack = now;
            float healthConsume = this.weapon.healthConsume;
            float magicConsume = this.weapon.magicConsume;
            this.healthPoint -= healthConsume;
            this.magicPoint -= magicConsume;
            if ((magicPoint < 0) || healthPoint < 0) {
                return;
            }
            if (Assets.isOpenSound())
                Assets.attackSound.play();

            float realDamage = this.opponent.takeDamage(this.attackDamage, this.abilityPower, this);
        }
        // play attack animation
        else if (now - lastAttack > 0 && opponent != null) {
            float s = (now - lastAttack) / (1000000000 / attackSpeed);
            this.setX((float) (posBeforeBattle.x + (opponent.getX() - posBeforeBattle.x) * s * 0.5));
            this.setY((float) (posBeforeBattle.y + (opponent.getY() - posBeforeBattle.y) * s * 0.5));
        }

    }

    /**
     * dropdown props method
     * @param props
     */
    public void dropDown(Props props) {
        backpack.removeValue(props, false);
    }

    /**
     * equip equipment
     * @param equipment
     */
    public void equip(Equipment equipment) {
        if (Assets.isOpenSound())
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

    /**
     * take dialogue with npc
     * @param actor npc actor that player talk with
     * @param dialogueJson dialogue json object
     */
    public void takeDialogue(NpcActor actor, JSONObject dialogueJson) {
        // if player is in dialogue, stop taking another dialogue
        if (!inDialogue) {
            this.inDialogue = true;
            actor.takeDialogue(this.getStage(), this, dialogueJson);
        }
    }

    /**
     * judge if player is in dialogue
     * @return return player if in dialogue
     */
    public boolean inDialogue() {
        return inDialogue;
    }

    /**
     * over the dialogue
     * call when dialogue is over
     */
    public void overDialogue() {
        this.inDialogue = false;
    }

    /**
     * update player attributes
     * call when player equip equipment
     */
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

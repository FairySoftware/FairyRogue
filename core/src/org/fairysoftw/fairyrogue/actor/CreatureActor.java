package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import org.fairysoftw.fairyrogue.Assets;

/**
 * creature actor(player, Npc, and monster)
 */
public class CreatureActor extends SpriteActor {
    //if actor is in battle
    boolean inBattle = false;
    //actor's opponent
    CreatureActor opponent = null;
    //actor's attack damage when attack the enemy
    protected float attackDamage = 0;
    //actor's ability power when attack the enemy
    protected float abilityPower = 0;
    //actor's attack speed when attack the enemy
    protected float attackSpeed = 0;
    //actor's health point when attack the enemy
    protected float healthPoint = 0;
    //actor's magic point when attack the enemy
    protected float magicPoint = 0;
    //actor's physical defence when attack the enemy
    protected float physicalDefence = 0;
    //actor's magical defence when attack the enemy
    protected float magicalDefence = 0;
    //time that the last attack happened
    protected long lastAttack = 0;
    //creator's position before battle
    protected Vector2 posBeforeBattle;

    /**
     * creature actor constructor
     * @param region
     */
    public CreatureActor(TextureRegion region) {
        super(region);
    }

    /**
     * creature actor constructor
     * @param object
     */
    public CreatureActor(MapObject object) {
        super(object);
        MapProperties properties = object.getProperties();
        attackDamage = (float) properties.get("attackDamage");
        abilityPower = (float) properties.get("abilityPower");
        attackSpeed = (float) properties.get("attackSpeed");
        healthPoint = (float) properties.get("healthPoint");
        magicPoint = (float) properties.get("magicPoint");
        physicalDefence = (float) properties.get("physicalDefence");
        magicalDefence = (float) properties.get("magicalDefence");
    }

    /**
     * take damage to its enemy
     * @param attackDamage
     * @param abilityPower
     * @param attacker
     * @return float
     */
    public float takeDamage(float attackDamage, float abilityPower, CreatureActor attacker) {
        //if actor is not in battle, start battle
        if (!inBattle) {
            takeBattle(attacker);
        }
        //calculate the read Damage and apply the damage to creator
        float realDamage = 0;
        if (attackDamage - physicalDefence > 0) {
            realDamage += attackDamage - physicalDefence;
        }
        if (abilityPower - magicalDefence > 0) {
            realDamage += abilityPower - magicalDefence;
        }
        healthPoint -= realDamage;
        //if the actor's heal point is less than 0, then he die
        if (this.healthPoint <= 0) {
            takeDeath();
        }
        return realDamage;
    }

    /**
     * take attack with its enemy
     */
    public void takeAttack() {
        long now = TimeUtils.nanoTime();
        if (now - lastAttack > 1000000000 / attackSpeed && !this.isDead() && opponent != null) {
            this.lastAttack = now;
            if (Assets.isOpenSound())
                Assets.attackSound.play();

            float realDamage = this.opponent.takeDamage(this.attackDamage, this.abilityPower, this);
        } else if (now - lastAttack > 0 && opponent != null) {
            float s = (now - lastAttack) / (1000000000 / attackSpeed);
            this.setX((float) (posBeforeBattle.x + (opponent.getX() - posBeforeBattle.x) * s * 0.5));
            this.setY((float) (posBeforeBattle.y + (opponent.getY() - posBeforeBattle.y) * s * 0.5));
        }
    }

    /**
     * take battle(set opponent and record the position before battle)
     * @param opponent
     */
    public void takeBattle(CreatureActor opponent) {
        this.opponent = opponent;
        this.inBattle = true;
        this.posBeforeBattle = new Vector2(this.getX(), this.getY());
    }

    /**
     * do when the actor is died
     */
    public void takeDeath() {
        if (Assets.isOpenSound())
            Assets.deathSound.play();

        this.remove();
        //TODO: add die animation
    }

    /**
     * judge if the actor is in battle
     * @return
     */
    public boolean isInBattle() {
        return inBattle;
    }

    /**
     * judge if the actor is dead
     * @return
     */
    public boolean isDead() {
        return healthPoint <= 0;
    }

    /**
     * actor's action and will be called per delta
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        //if the actor is in battle
        if (this.inBattle) {
            //if the actor is dead, reset its situation,position and oponent
            if (this.opponent.isDead()) {
                this.inBattle = false;
                this.opponent = null;
                this.setX(posBeforeBattle.x);
                this.setY(posBeforeBattle.y);
            }
            //take attack
            takeAttack();
        }
    }

    /**
     * get attack damage of actor
     * @return attackDamage
     */
    public float getAttackDamage() {
        return attackDamage;
    }

    /**
     * get ability power of actor
     * @return abilityPower
     */
    public float getAbilityPower() {
        return abilityPower;
    }

    /**
     * get attack speed of actor
     * @return attackSpeed
     */
    public float getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * git health point of actor
     * @return healthPoint
     */
    public float getHealthPoint() {
        return healthPoint;
    }

    /**
     * get magic point of actor
     * @return magicPoint
     */
    public float getMagicPoint() {
        return magicPoint;
    }

    /**
     * get physical defence of actor
     * @return physicalDefence
     */
    public float getPhysicalDefence() {
        return physicalDefence;
    }

    /**
     * get magical defense of actor
     * @return magicalDefence
     */
    public float getMagicalDefence() {
        return magicalDefence;
    }

}

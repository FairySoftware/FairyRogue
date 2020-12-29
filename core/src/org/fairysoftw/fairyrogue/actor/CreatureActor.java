package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import org.fairysoftw.fairyrogue.Assets;

public class CreatureActor extends SpriteActor {
    boolean inBattle = false;
    CreatureActor opponent = null;
    protected float attackDamage = 0;
    protected float abilityPower = 0;
    protected float attackSpeed = 0;
    protected float healthPoint = 0;
    protected float magicPoint = 0;
    protected float physicalDefence = 0;
    protected float magicalDefence = 0;
    protected long lastAttack = 0;
    protected Vector2 posBeforeBattle;

    public CreatureActor(TextureRegion region) {
        super(region);
    }

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

    public float takeDamage(float attackDamage, float abilityPower, CreatureActor attacker) {
        if (!inBattle) {
            takeBattle(attacker);
        }
        float realDamage = 0;
        if (attackDamage - physicalDefence > 0) {
            realDamage += attackDamage - physicalDefence;
        }
        if (abilityPower - magicalDefence > 0) {
            realDamage += abilityPower - magicalDefence;
        }
        healthPoint -= realDamage;
        if (this.healthPoint <= 0) {
            takeDeath();
        }
        return realDamage;
    }

    public void takeAttack() {
        long now = TimeUtils.nanoTime();
        if (now - lastAttack > 1000000000 / attackSpeed && !this.isDead() && opponent != null) {
            this.lastAttack = now;

            Assets.attackSound.play();

            float realDamage = this.opponent.takeDamage(this.attackDamage, this.abilityPower, this);
        }
        else if (now - lastAttack > 0 && opponent != null) {
            float s = (now - lastAttack) / (1000000000 / attackSpeed);
            this.setX((float) (posBeforeBattle.x + (opponent.getX() - posBeforeBattle.x) * s * 0.5));
            this.setY((float) (posBeforeBattle.y + (opponent.getY() - posBeforeBattle.y) * s * 0.5));
        }
    }

    public void takeBattle(CreatureActor opponent) {
        this.opponent = opponent;
        this.inBattle = true;
        this.posBeforeBattle = new Vector2(this.getX(), this.getY());
    }

    public void takeDeath() {

        Assets.deathSound.play();

        this.remove();
        //TODO: add die animation
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public boolean isDead() {
        return healthPoint <= 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.inBattle) {
            if (this.opponent.isDead()) {
                this.inBattle = false;
                this.opponent = null;
                this.setX(posBeforeBattle.x);
                this.setY(posBeforeBattle.y);
            }
            takeAttack();
        }
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAbilityPower() {
        return abilityPower;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getHealthPoint() {
        return healthPoint;
    }

    public float getMagicPoint() {
        return magicPoint;
    }

    public float getPhysicalDefence() {
        return physicalDefence;
    }

    public float getMagicalDefence() {
        return magicalDefence;
    }

}

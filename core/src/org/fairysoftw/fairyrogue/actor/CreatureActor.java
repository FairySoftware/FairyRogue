package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

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
            inBattle = true;
            this.opponent = attacker;
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
        if (now - lastAttack > 1000000000 / attackSpeed && !this.isDead()) {
            lastAttack = now;
            float realDamage = this.opponent.takeDamage(this.attackDamage, this.abilityPower, this);
            Gdx.app.debug(this.getName(), "take " + realDamage + " damage to " + opponent.getName());
        }
    }

    public void takeBattle(CreatureActor opponent) {
        this.opponent = opponent;
        this.inBattle = true;
    }

    public void takeDeath() {
        this.remove();
        Gdx.app.debug(this.getName(), "is dead");
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
            }
            takeAttack();
        }
    }
}

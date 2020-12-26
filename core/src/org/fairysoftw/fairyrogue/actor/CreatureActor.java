package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class CreatureActor extends SpriteActor {
    protected float attackDamage = 0;
    protected float abilityPower = 0;
    protected float healthPoint = 0;
    protected float magicPoint = 0;
    protected float physicalDefence = 0;
    protected float magicalDefence = 0;

    public CreatureActor(TextureRegion region) {
        super(region);
    }

    public CreatureActor(MapObject object) {
        super(object);
    }

    public float takeDamage(Attack attack) {
        float realDamage = 0;
        if (attack.attackDamage - physicalDefence > 0) {
            realDamage += attack.attackDamage - physicalDefence;
        }
        if (attack.abilityPower - magicalDefence > 0) {
            realDamage += attack.abilityPower - magicalDefence;
        }
        healthPoint -= realDamage;
        return realDamage;
    }

    public Attack takeAttack() {
        return new Attack(this.attackDamage, this.abilityPower);
    }

    public void takeDeath() {
        //TODO: add die animation
    }
}

package org.fairysoftw.fairyrogue.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Attack {
    public float attackDamage = 0;
    public float abilityPower = 0;
    public Actor attacker = null;
    public Actor beAttacker = null;

    public Attack(float attackDamage, float abilityPower) {
        this.abilityPower = abilityPower;
        this.attackDamage = attackDamage;
    }
}

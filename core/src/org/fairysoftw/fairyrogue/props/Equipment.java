package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

public class Equipment extends Props {
    public enum EquipmentType {
        WEAPON,
        APPAREL_HEAD,
        APPAREL_UPPER_BODY,
        APPAREL_LOWER_BODY,
        ACCESSORIES,
    }

    public Equipment(MapProperties properties) {

    }

    public EquipmentType type;
    public float attackDamage = 0;
    public float attackSpeed = 0;
    public float abilityPower = 0;
    public float physicalDefence = 0;
    public float magicalDefence = 0;
    public float healthConsume = 0;
    public float magicConsume = 0;
    public float durability = 0;

    public Equipment(EquipmentType type,
                     float attackDamage,
                     float attackSpeed,
                     float abilityPower,
                     float physicalDefence,
                     float magicalDefence,
                     float healthConsume,
                     float magicConsume,
                     float durability) {
        this.type = type;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.abilityPower = abilityPower;
        this.physicalDefence = physicalDefence;
        this.magicalDefence = magicalDefence;
        this.healthConsume = healthConsume;
        this.magicConsume = magicConsume;
        this.durability = durability;
    }
}

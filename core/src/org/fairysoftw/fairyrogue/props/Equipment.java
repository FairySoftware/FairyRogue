package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapProperties;
import org.fairysoftw.fairyrogue.actor.PropsActor;

public class Equipment extends Props {
    public enum EquipmentType {
        NONE,
        WEAPON,
        APPAREL_HEAD,
        APPAREL_UPPER_BODY,
        APPAREL_LOWER_BODY,
        ACCESSORIES,
    }

    public enum EquipmentQuality {
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

    public Equipment(MapProperties properties) {
        this.propsType = PropsActor.PropsType.EQUIPMENT;
        switch ((String)properties.get("type"))
        {
            case "weapon":
                this.equipmentType = EquipmentType.WEAPON;
                break;
            case "apparel_head":
                this.equipmentType = EquipmentType.APPAREL_HEAD;
                break;
            case "apparel_upper_body":
                this.equipmentType = EquipmentType.APPAREL_UPPER_BODY;
                break;
            case "apparel_lower_body":
                this.equipmentType = EquipmentType.APPAREL_LOWER_BODY;
                break;
            case "accessories":
                this.equipmentType = EquipmentType.ACCESSORIES;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + properties.get("type"));
        }
        attackDamage = (float)properties.get("attackDamage");
        attackSpeed = (float)properties.get("attackSpeed");
        abilityPower = (float)properties.get("abilityPower");
        physicalDefence = (float)properties.get("physicalDefence");
        magicalDefence = (float)properties.get("magicalDefence");
        healthConsume = (float)properties.get("healthConsume");
        magicConsume = (float)properties.get("magicConsume");
        durability = (float)properties.get("durability");

    }

    public Equipment(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public EquipmentType equipmentType;
    public EquipmentQuality rarity;
    public float attackDamage = 0;
    public float attackSpeed = 0;
    public float abilityPower = 0;
    public float physicalDefence = 0;
    public float magicalDefence = 0;
    public float healthConsume = 0;
    public float magicConsume = 0;
    public float durability = 0;

    public Equipment(EquipmentType equipmentType,
                     float attackDamage,
                     float attackSpeed,
                     float abilityPower,
                     float physicalDefence,
                     float magicalDefence,
                     float healthConsume,
                     float magicConsume,
                     float durability) {
        this.equipmentType = equipmentType;
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

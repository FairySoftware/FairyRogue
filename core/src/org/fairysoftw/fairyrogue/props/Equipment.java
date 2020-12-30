package org.fairysoftw.fairyrogue.props;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import org.fairysoftw.fairyrogue.actor.PropsActor;

/** {@inheritDoc}
 * equipment that inherit porps
 */
public class Equipment extends Props {
    public EquipmentType equipmentType;
    public EquipmentQuality rarity;
    // equipment attribute of equipment
    // attack damage means physical damage
    public float attackDamage = 0;
    // attack speed means attack times per second
    public float attackSpeed = 0;
    // ability power means magical damage
    public float abilityPower = 0;
    // physical defence means ability to defence physical damage
    public float physicalDefence = 0;
    // magical defence means ability to defence magical damage
    public float magicalDefence = 0;
    // health consume means the health point every attack the equipment consume
    public float healthConsume = 0;
    // magic consume means the magic point every attack the equipment consume
    public float magicConsume = 0;
    // durability means the durability of equipment, will decrease every attack
    public float durability = 0;

    /**
     * enum type that represent equipment type
     */
    public enum EquipmentType {
        NONE,
        WEAPON,
        APPAREL_HEAD,
        APPAREL_UPPER_BODY,
        APPAREL_LOWER_BODY,
        ACCESSORIES,
    }

    /**
     * enum type that represent equipment type
     */
    public enum EquipmentQuality {
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

    /**
     * equipment constructor
     * @param mapObject map object of equipment
     */
    public Equipment(MapObject mapObject) {
        super(mapObject);
        MapProperties properties = mapObject.getProperties();
        this.propsType = PropsActor.PropsType.EQUIPMENT;
        switch ((String) properties.get("equipment_type")) {
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
        // set equipment attribute
        attackDamage = (float) properties.get("attackDamage");
        attackSpeed = (float) properties.get("attackSpeed");
        abilityPower = (float) properties.get("abilityPower");
        physicalDefence = (float) properties.get("physicalDefence");
        magicalDefence = (float) properties.get("magicalDefence");
        healthConsume = (float) properties.get("healthConsume");
        magicConsume = (float) properties.get("magicConsume");
        durability = (float) properties.get("durability");

    }

    /**
     * constructor of equipment
     * @param equipmentType equipment type enum class instance
     */
    public Equipment(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    /**
     * constructor of equipment
     * @param equipmentType equipment type
     * @param attackDamage attack damage
     * @param attackSpeed attack speed
     * @param abilityPower ability power
     * @param physicalDefence physical defence
     * @param magicalDefence magical defence
     * @param healthConsume health consume
     * @param magicConsume magic consume
     * @param durability durability
     */
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

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

public class ChestplateAbility extends ArmorAbility {

    public ChestplateAbility(double armor) {
        super(armor, 0, 0, "chest");
    }

    public ChestplateAbility(double armor, double heatInsulation, double coldInsulation) {
        super(armor, heatInsulation, coldInsulation, "chest");
    }

}
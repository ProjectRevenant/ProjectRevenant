package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

public class LeggingAbility extends ArmorAbility {

    public LeggingAbility(double armor) {
        super(armor, 0, 0, "legging");
    }

    public LeggingAbility(double armor, double heatInsulation, double coldInsulation) {
        super(armor, heatInsulation, coldInsulation, "legging");
    }

}
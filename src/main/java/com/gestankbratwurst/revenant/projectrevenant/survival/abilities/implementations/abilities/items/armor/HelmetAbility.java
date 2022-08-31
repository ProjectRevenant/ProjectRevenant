package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

public class HelmetAbility extends ArmorAbility {

    public HelmetAbility(double armor) {
        super(armor, 0, 0, "helmet");
    }

    public HelmetAbility(double armor, double heatInsulation, double coldInsulation) {
        super(armor, heatInsulation, coldInsulation, "helmet");
    }


}
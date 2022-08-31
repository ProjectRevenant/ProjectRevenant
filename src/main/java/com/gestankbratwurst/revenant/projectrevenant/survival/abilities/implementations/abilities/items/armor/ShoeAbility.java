package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

public class ShoeAbility extends ArmorAbility{

  public ShoeAbility(double armor){
    super(armor, 0, 0, "shoe");
  }

  public ShoeAbility(double armor, double heatInsulation, double coldInsulation) {
    super(armor, heatInsulation, coldInsulation, "shoe");
  }

}
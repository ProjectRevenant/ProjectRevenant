package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.campfire;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class CampfireColdResistance extends BodyAttributeModifier {

  public CampfireColdResistance() {
    super("campfire-cold-resistance", BodyAttribute.COLD_RESISTANCE);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand + 7.5;
  }
}

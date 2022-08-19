package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wet;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class WetColdResistModifier extends BodyAttributeModifier {
  public WetColdResistModifier() {
    super("wet-cold-resist", BodyAttribute.COLD_RESISTANCE);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand - 5.0;
  }
}

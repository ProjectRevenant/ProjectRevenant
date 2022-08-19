package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wet;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class WetHeatResistModifier extends BodyAttributeModifier {
  public WetHeatResistModifier() {
    super("wet-heat-resist", BodyAttribute.HEAT_RESISTANCE);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand + 5.0;
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wet;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class WetTempModifier extends BodyAttributeModifier {
  public WetTempModifier() {
    super("wet-temp-shift", BodyAttribute.TEMPERATURE_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand - 0.0001;
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.leg;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HealingLegWalkModifier extends BodyAttributeModifier {
  public HealingLegWalkModifier() {
    super("healing-leg-modifier", BodyAttribute.SPEED);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand * 0.85;
  }
}

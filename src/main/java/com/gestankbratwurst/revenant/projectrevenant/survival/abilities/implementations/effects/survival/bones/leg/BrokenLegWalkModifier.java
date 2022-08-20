package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.leg;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class BrokenLegWalkModifier extends BodyAttributeModifier {
  public BrokenLegWalkModifier() {
    super("broken-leg-modifier", BodyAttribute.SPEED);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand * 0.6;
  }
}

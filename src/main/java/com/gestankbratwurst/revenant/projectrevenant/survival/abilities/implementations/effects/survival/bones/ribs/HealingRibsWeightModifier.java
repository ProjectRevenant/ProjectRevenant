package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.ribs;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HealingRibsWeightModifier extends BodyAttributeModifier {
  public HealingRibsWeightModifier() {
    super("healing-ribs-mod", BodyAttribute.WEIGHT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand * 0.85;
  }
}

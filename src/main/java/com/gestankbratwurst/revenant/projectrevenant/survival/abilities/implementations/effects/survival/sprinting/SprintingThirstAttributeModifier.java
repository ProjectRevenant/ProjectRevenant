package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.sprinting;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class SprintingThirstAttributeModifier extends BodyAttributeModifier {

  private final static double thirstScalar = 0.5;

  public SprintingThirstAttributeModifier() {
    super("sprinting-thirst-mod", BodyAttribute.WATER_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand >= 0 ? operand * (1.0 - thirstScalar) : operand * (1.0 + thirstScalar);
  }
}


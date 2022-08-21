package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.sprinting;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class SprintingHungerAttributeModifier extends BodyAttributeModifier {

  private final static double nutritionScalar = 0.5;

  public SprintingHungerAttributeModifier() {
    super("sprinting-hunger-mod", BodyAttribute.NUTRITION_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand >= 0 ? operand * (1.0 - nutritionScalar) : operand * (1.0 + nutritionScalar);
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.undercooling;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class UndercoolingNutritionModifier extends BodyAttributeModifier {
  public UndercoolingNutritionModifier() {
    super("undercooling-nutrition", BodyAttribute.NUTRITION_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand > 0 ? operand : operand * 4;
  }
}

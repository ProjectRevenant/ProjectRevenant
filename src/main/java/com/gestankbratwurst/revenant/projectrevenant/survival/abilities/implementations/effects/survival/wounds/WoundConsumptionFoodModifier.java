package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.bson.Document;

public class WoundConsumptionFoodModifier extends BodyAttributeModifier {

  private static final double mod = 0.15;

  public WoundConsumptionFoodModifier() {
    super("wound-food-consumption-mod", BodyAttribute.NUTRITION_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand < 0 ? operand * (1.0 + mod) : operand * (1.0 - mod) ;
  }
}

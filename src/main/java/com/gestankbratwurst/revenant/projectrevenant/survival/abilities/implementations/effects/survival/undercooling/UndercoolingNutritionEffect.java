package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.undercooling;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class UndercoolingNutritionEffect extends AbilityEffect<Body> {
  public UndercoolingNutritionEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION_SHIFT).addModifier(new BodyAttributeModifier("undercooling-nutrition-mod", BodyAttribute.NUTRITION_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand > 0 ? operand : operand * 4;
      }
    });
  }
}

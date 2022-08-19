package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.undercooling;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class UndercoolingNutritionEffect extends AbilityEffect<Body> {
  public UndercoolingNutritionEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "undercooling-nutrition-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION_SHIFT).addModifier(new UndercoolingNutritionModifier());
  }
}

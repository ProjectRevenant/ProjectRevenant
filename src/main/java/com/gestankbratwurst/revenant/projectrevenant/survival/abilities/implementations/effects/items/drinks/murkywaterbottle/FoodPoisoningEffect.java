package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.murkywaterbottle;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class FoodPoisoningEffect extends AbilityEffect<Body> {
  public FoodPoisoningEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "food-poisoning-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION).applyToCurrentValue(current -> current - (0.075 / 20));
    element.getAttribute(BodyAttribute.SPEED).applyToCurrentValue(current -> current * 0.90);
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).applyToCurrentValue(current -> current - (1.0 / (20 * 60)));
  }
}

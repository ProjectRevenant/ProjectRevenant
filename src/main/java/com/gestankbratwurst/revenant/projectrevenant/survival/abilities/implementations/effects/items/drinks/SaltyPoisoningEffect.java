package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class SaltyPoisoningEffect extends AbilityEffect<Body> {
  public SaltyPoisoningEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "salt-poisoning");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).applyToCurrentValue(current -> current - (0.075 / 20));
    element.getAttribute(BodyAttribute.MELEE_DAMAGE).applyToCurrentValue(current -> current * 0.90);
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).applyToCurrentValue(current -> current - (1.0 / (20 * 60)));
  }
}

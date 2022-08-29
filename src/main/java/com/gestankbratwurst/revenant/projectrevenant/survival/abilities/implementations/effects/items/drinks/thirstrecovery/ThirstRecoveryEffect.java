package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.thirstrecovery;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class ThirstRecoveryEffect extends AbilityEffect<Body> {
  public ThirstRecoveryEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "thirst-recovery");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).applyToCurrentValue(current -> current + (0.025 / 20));
  }
}

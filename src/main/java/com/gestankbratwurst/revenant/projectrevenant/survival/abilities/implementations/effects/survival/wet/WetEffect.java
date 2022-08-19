package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wet;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class WetEffect extends AbilityEffect<Body> {
  public WetEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "wet");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.TEMPERATURE_SHIFT).addModifier(new WetTempModifier());
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.campfire;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class CampfireBodyEffect extends AbilityEffect<Body> {
  public CampfireBodyEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "campfire");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.COLD_RESISTANCE).addModifier(new CampfireColdResistance());
  }
}

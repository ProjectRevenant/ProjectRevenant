package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.ribs;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class HealingRibsEffect extends AbilityEffect<Body> {
  public HealingRibsEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "healing-ribs-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WEIGHT).addModifier(new HealingRibsWeightModifier());
  }
}

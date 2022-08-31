package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.leg;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class HealingLegEffect extends AbilityEffect<Body> {
  public HealingLegEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.SPEED).addModifier(new HealingLegWalkModifier());
  }
}

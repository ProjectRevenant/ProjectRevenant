package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.leg;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class BrokenLegEffect extends AbilityEffect<Body> {
  public BrokenLegEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "broke-leg-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.SPEED).addModifier(new BrokenLegWalkModifier());
  }
}

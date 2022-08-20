package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.arm;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class BrokenArmEffect extends AbilityEffect<Body> {
  public BrokenArmEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "broken-arm-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.MELEE_DAMAGE).addModifier(new BrokenArmDamageModifier());
  }
}

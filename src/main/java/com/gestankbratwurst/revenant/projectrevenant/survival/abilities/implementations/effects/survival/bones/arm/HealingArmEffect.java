package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.arm;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import net.kyori.adventure.text.Component;

public class HealingArmEffect extends AbilityEffect<Body> {
  public HealingArmEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "healing-arm-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.MELEE_DAMAGE).addModifier(new HealingArmDamageModifier());
  }
}

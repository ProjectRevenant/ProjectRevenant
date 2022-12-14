package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.skull;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HealingSkullEffect extends AbilityEffect<Body> {
  public HealingSkullEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.RANGED_DAMAGE).addModifier(new BodyAttributeModifier("healing-skull-ranged-mod", BodyAttribute.RANGED_DAMAGE) {
      @Override
      public double applyAsDouble(double operand) {
        return operand * 0.85;
      }
    });
  }
}

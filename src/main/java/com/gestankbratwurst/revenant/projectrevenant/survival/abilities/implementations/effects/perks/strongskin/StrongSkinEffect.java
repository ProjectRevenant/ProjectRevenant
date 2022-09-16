package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.perks.strongskin;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class StrongSkinEffect extends AbilityEffect<Body> {
  public StrongSkinEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH).addModifier(new BodyAttributeModifier("strong-skin",  BodyAttribute.HEALTH) {
      @Override
      public double applyAsDouble(double operand) {
        return operand * 1.1;
      }
    });
  }
}

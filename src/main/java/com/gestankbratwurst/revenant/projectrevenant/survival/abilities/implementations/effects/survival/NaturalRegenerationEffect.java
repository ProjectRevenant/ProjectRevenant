package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class NaturalRegenerationEffect extends AbilityEffect<Body> {

  //~ 1Hp/s
  public final static double rate = 0.0008;

  public NaturalRegenerationEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).addModifier(new BodyAttributeModifier("natural-regeneration-mod", BodyAttribute.HEALTH_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + rate;
      }
    });
  }

}

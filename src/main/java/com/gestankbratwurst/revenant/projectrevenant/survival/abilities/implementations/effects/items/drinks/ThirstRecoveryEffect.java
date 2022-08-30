package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class ThirstRecoveryEffect extends AbilityEffect<Body> {
  //0.001 pro Tick, 0.02 pro Sekunde
  public final static double RATE = 0.001;

  public ThirstRecoveryEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new BodyAttributeModifier("thirst-recovery-mod", BodyAttribute.WATER_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + RATE;
      }
    });
  }
}

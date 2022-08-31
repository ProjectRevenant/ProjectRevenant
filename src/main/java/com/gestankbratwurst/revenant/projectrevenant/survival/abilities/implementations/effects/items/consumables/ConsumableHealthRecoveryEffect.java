package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class ConsumableHealthRecoveryEffect extends AbilityEffect<Body> {


  private final double rate;

  public ConsumableHealthRecoveryEffect() {
    this(0.0);
  }

  public ConsumableHealthRecoveryEffect(double rate){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.rate = rate;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH).addModifier(new BodyAttributeModifier("consumable-health-recovery-mod", BodyAttribute.HEALTH) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + rate;
      }
    });
  }

}
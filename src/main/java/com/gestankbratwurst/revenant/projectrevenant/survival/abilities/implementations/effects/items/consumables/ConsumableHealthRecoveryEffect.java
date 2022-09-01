package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import lombok.Getter;

import java.time.Duration;

public class ConsumableHealthRecoveryEffect extends AbilityEffect<Body> {


  @Getter
  private Duration duration;
  @Getter
  private double amount;
  private double rate;

  public ConsumableHealthRecoveryEffect() {
    this(0.0, Duration.ZERO);
  }

  public ConsumableHealthRecoveryEffect(double amount, Duration duration){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.duration = duration;
    this.amount = amount;
    this.rate = amount / (duration.getSeconds() * 20);
  }

  public void setAmount(double amount, Duration newDuration){
    this.amount = amount;
    this.duration = newDuration;
    this.rate = amount / (duration.getSeconds() * 20);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).addModifier(new BodyAttributeModifier("consumable-health-recovery-mod", BodyAttribute.HEALTH_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + rate;
      }
    });
  }

}
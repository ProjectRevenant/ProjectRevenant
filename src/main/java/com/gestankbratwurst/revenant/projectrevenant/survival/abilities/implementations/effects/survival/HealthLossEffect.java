package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HealthLossEffect extends AbilityEffect<Body> {

  private final double healthLoss;
  private final String sufix;
  public HealthLossEffect() {
    this(0.0, "NULL");
  }

  /**
   * @param loss positive number, HP loss per tick
   * @param sufix unique sufix
   */
  public HealthLossEffect(double loss, String sufix){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.healthLoss = loss;
    this.sufix = sufix;
  }


  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).addModifier(new BodyAttributeModifier("heath-loss-" + sufix, BodyAttribute.HEALTH_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return 0 - healthLoss;
      }
    });
  }
}

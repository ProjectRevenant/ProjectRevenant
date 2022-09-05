package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class PercentageSpeedEffect extends AbilityEffect<Body> {
  
  private final double mod;
  private final String sufix;
  
  public PercentageSpeedEffect() {
    this(0.0, "NULL");
  }
  
  public PercentageSpeedEffect(double mod, String sufix){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.mod = mod;
    this.sufix = sufix;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.SPEED).addModifier(new BodyAttributeModifier("speed-mod-" + sufix, BodyAttribute.SPEED) {
      @Override
      public double applyAsDouble(double operand) {
        return operand * mod;
      }
    });
  }
}

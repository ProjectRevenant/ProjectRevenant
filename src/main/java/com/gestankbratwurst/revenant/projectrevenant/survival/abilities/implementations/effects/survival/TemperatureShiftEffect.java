package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class TemperatureShiftEffect extends AbilityEffect<Body> {

  private final double amount;
  private final String sufix;

  /**
   *
   * @param amount positive change amount per tick
   * @param sufix unique sufix
   */
  public TemperatureShiftEffect(double amount, String sufix) {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.amount = amount;
    this.sufix = sufix;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.TEMPERATURE_SHIFT).addModifier(new BodyAttributeModifier("temp-shift-" + sufix, BodyAttribute.TEMPERATURE_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + amount;
      }
    });
  }
}

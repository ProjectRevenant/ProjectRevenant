package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HeatInsulationEffect extends AbilityEffect<Body> {


  private final double amount;
  private final String sufix;

  public HeatInsulationEffect(double amount, String sufix) {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.amount = amount;
    this.sufix = sufix;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEAT_RESISTANCE).addModifier(new BodyAttributeModifier("armor-heat-insulation-mod-" + sufix, BodyAttribute.HEAT_RESISTANCE) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + amount;
      }
    });

  }
}

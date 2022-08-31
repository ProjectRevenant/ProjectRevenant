package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import lombok.Getter;

public class ColdInsulationEffect extends AbilityEffect<Body> {

  @Getter
  private final double amount;
  private final String sufix;

  public ColdInsulationEffect(double amount, String sufix) {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.amount = amount;
    this.sufix = sufix;
  }

  @Override
  public void cast(Body element) {

    element.getAttribute(BodyAttribute.COLD_RESISTANCE).addModifier(new BodyAttributeModifier("armor-cold-insulation-mod-" + sufix, BodyAttribute.COLD_RESISTANCE) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + amount;
      }
    });

  }
}

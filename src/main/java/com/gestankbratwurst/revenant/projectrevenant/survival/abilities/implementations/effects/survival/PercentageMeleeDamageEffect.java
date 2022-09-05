package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class PercentageMeleeDamageEffect extends AbilityEffect<Body> {

  private final double mod;
  private final String sufix;

  public PercentageMeleeDamageEffect() {
    this(0.0, "NULL");
  }

  public PercentageMeleeDamageEffect(double mod, String sufix){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.mod = mod;
    this.sufix = sufix;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.MELEE_DAMAGE).addModifier(new BodyAttributeModifier("damage-mod-" + sufix, BodyAttribute.MELEE_DAMAGE) {
      @Override
      public double applyAsDouble(double operand) {
        return operand * mod;
      }
    });
  }
}

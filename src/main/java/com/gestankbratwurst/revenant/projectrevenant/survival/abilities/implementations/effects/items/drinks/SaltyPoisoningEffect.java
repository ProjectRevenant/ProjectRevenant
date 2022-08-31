package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class SaltyPoisoningEffect extends AbilityEffect<Body> {
  public SaltyPoisoningEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new BodyAttributeModifier("salt-water-mod", BodyAttribute.WATER_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand - (0.075 / 20);
      }
    });
    element.getAttribute(BodyAttribute.MELEE_DAMAGE).addModifier(new BodyAttributeModifier("salt-melee-damage-mod", BodyAttribute.MELEE_DAMAGE) {
      @Override
      public double applyAsDouble(double operand) {
        return operand * 0.90;
      }
    });
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).addModifier(new BodyAttributeModifier("salt-health-mod", BodyAttribute.HEALTH) {
      @Override
      public double applyAsDouble(double operand) {
        return operand - (1.0 / (20 * 60));
      }
    });
  }
}

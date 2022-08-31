package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class FoodPoisoningEffect extends AbilityEffect<Body> {
  public FoodPoisoningEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION).addModifier(new BodyAttributeModifier("poisoning-nutrition-mod", BodyAttribute.NUTRITION) {
      @Override
      public double applyAsDouble(double operand) {
        return operand - (0.075 / 20);
      }
    });
    element.getAttribute(BodyAttribute.SPEED).addModifier(new BodyAttributeModifier("poisoning-speed-mod", BodyAttribute.SPEED) {
      @Override
      public double applyAsDouble(double operand) {
        return operand * 0.90;
      }
    });
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).addModifier(new BodyAttributeModifier("poisoning-health-mod", BodyAttribute.HEALTH_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand - (1.0 / (20 * 60));
      }
    });
  }
}

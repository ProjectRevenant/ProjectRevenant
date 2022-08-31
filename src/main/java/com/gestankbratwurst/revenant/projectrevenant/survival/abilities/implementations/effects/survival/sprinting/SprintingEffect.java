package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.sprinting;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class SprintingEffect extends AbilityEffect<Body> {

  private final static double nutritionScalar = 0.5;
  private final static double thirstScalar = 0.5;
  private final static double warmingEffect = 0.1 / 1200;

  public SprintingEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION_SHIFT).addModifier(new BodyAttributeModifier("sprinting-nutrition-mod", BodyAttribute.NUTRITION_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand >= 0 ? operand * (1.0 - nutritionScalar) : operand * (1.0 + nutritionScalar);
      }
    });
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new BodyAttributeModifier("sprinting-thirst-mod", BodyAttribute.WATER_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand >= 0 ? operand * (1.0 - thirstScalar) : operand * (1.0 + thirstScalar);
      }
    });
    element.getAttribute(BodyAttribute.TEMPERATURE_SHIFT).addModifier(new BodyAttributeModifier("sprinting-warming-mod", BodyAttribute.TEMPERATURE_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + warmingEffect;
      }
    });
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class NutritionRecoveryEffect extends AbilityEffect<Body> {

  //0.3 pro Tick, 6 pro Sekunde
  public final static double RATE = 0.3;

  public NutritionRecoveryEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION_SHIFT).addModifier(new BodyAttributeModifier("nutrition-recovery-mod", BodyAttribute.NUTRITION_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + RATE;
      }
    });
  }

}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overheating;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class OverheatingWaterEffect extends AbilityEffect<Body> {
  public OverheatingWaterEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new BodyAttributeModifier("overheating-water-mod", BodyAttribute.WATER_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand > 0 ? operand : operand * 4;
      }
    });
  }
}

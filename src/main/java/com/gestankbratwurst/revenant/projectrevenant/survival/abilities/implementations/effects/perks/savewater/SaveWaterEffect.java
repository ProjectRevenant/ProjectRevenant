package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.perks.savewater;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class SaveWaterEffect extends AbilityEffect<Body> {
  public SaveWaterEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new BodyAttributeModifier("save-water-effect", BodyAttribute.WATER_SHIFT) {
      @Override
      public double applyAsDouble(double operand) {
        return operand < 0 ? operand * 0.9 : operand;
      }
    });
  }

}

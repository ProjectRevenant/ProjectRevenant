package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overheating;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class OverheatingWaterEffect extends AbilityEffect<Body> {
  public OverheatingWaterEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "overheating-water-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new OverheatingWaterModifier());
  }
}

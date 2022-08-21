package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.sprinting;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class SprintingEffect extends AbilityEffect<Body> {
  public SprintingEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "sprinting-effect");
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.NUTRITION_SHIFT).addModifier(new SprintingHungerAttributeModifier());
    element.getAttribute(BodyAttribute.WATER_SHIFT).addModifier(new SprintingThirstAttributeModifier());
    element.getAttribute(BodyAttribute.TEMPERATURE_SHIFT).addModifier(new SprintingWarmingAttributeModifier());
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import lombok.Getter;
import lombok.Setter;

public class BleedingEffect extends AbilityEffect<Body> {
  public BleedingEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "bleeding-effect");
  }

  @Getter
  @Setter
  private int intensity = 1;

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH_SHIFT).addModifier(new BleedingModifier(intensity));
  }
}

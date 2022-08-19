package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overweight;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import lombok.Getter;

public class OverweightEffect extends AbilityEffect<Body> {

  public OverweightEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "overweight");
  }

  @Getter
  private boolean critical = false;
  @Getter
  private boolean active = false;

  @Override
  public void cast(Body element) {
    BodyAttribute speed = element.getAttribute(BodyAttribute.SPEED);
    BodyAttribute weight = element.getAttribute(BodyAttribute.WEIGHT);
    double overWeight = (1.0 / weight.getMaxValueModified() * weight.getCurrentValue()) - 1.0;
    if (overWeight < 0) {
      active = false;
      return;
    }
    active = true;
    if (overWeight < 0.15) {
      critical = false;
      speed.addModifier(new OverweightSpeedModifier(0.5));
      return;
    }
    critical = true;
    speed.addModifier(new OverweightSpeedModifier(0.15));
  }
}

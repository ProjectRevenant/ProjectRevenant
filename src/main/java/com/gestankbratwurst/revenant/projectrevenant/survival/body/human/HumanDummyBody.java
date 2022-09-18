package com.gestankbratwurst.revenant.projectrevenant.survival.body.human;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;

public class HumanDummyBody extends Body {

  public HumanDummyBody(double currentHealth, double currentArmor, double currentSpeed) {
    super();
    BodyAttribute health = this.getAttribute(BodyAttribute.HEALTH);
    health.setMaxValue(1000.0);
    health.setMinValue(0.0);
    health.setCurrentValue(currentHealth);

    BodyAttribute armor = this.getAttribute(BodyAttribute.PHYSICAL_ARMOR);
    armor.setMaxValue(1000.0);
    armor.setMinValue(0.0);
    armor.setCurrentValue(currentArmor);

    BodyAttribute speed = this.getAttribute(BodyAttribute.SPEED);
    speed.setMaxValue(100.0);
    speed.setMinValue(0.0);
    speed.setCurrentValue(currentSpeed);
  }

}

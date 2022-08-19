package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class BrokenArmDamageModifier extends BodyAttributeModifier {
  public BrokenArmDamageModifier() {
    super("broken-arm-damage-mod", BodyAttribute.MELEE_DAMAGE);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand * 0.6;
  }
}

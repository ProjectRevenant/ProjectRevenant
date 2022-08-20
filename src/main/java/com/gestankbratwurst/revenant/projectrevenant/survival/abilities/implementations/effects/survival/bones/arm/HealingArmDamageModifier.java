package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.arm;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HealingArmDamageModifier extends BodyAttributeModifier {
  public HealingArmDamageModifier() {
    super("healing-arm-damage-modifier", BodyAttribute.MELEE_DAMAGE);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand * 0.85;
  }
}

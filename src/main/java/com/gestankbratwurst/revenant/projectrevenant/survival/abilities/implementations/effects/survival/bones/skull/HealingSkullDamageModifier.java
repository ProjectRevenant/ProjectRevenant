package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.skull;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class HealingSkullDamageModifier extends BodyAttributeModifier {
  public HealingSkullDamageModifier() {
    super("healing-skull-ranged-mod", BodyAttribute.RANGED_DAMAGE);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand * 0.85;
  }
}

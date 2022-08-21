package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.sprinting;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class SprintingWarmingAttributeModifier extends BodyAttributeModifier {

  private final static double warmingEffect = 0.1 / 1200;

  public SprintingWarmingAttributeModifier() {
    super("sprinting-warmth-mod", BodyAttribute.TEMPERATURE_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand + warmingEffect;
  }
}

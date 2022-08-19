package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overheating;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class OverheatingWaterModifier extends BodyAttributeModifier {
  public OverheatingWaterModifier() {
    super("overheating-water-mod", BodyAttribute.WATER_SHIFT);
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand > 0 ? operand : operand * 4;
  }
}

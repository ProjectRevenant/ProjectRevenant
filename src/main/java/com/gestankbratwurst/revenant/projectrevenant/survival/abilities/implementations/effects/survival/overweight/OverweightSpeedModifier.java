package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overweight;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.jetbrains.annotations.NotNull;

public class OverweightSpeedModifier extends BodyAttributeModifier {

  public OverweightSpeedModifier() {
    this(0.0);
  }

  public OverweightSpeedModifier(double scalar) {
    super("overweight-mod", BodyAttribute.SPEED);
    this.scalar = scalar;
  }

  private final double scalar;

  @Override
  public double applyAsDouble(double operand) {
    return operand * scalar;
  }

  @Override
  public int compareTo(@NotNull BodyAttributeModifier other) {
    return Double.compare(1 - scalar, 1 - ((OverweightSpeedModifier) other).scalar);
  }
}

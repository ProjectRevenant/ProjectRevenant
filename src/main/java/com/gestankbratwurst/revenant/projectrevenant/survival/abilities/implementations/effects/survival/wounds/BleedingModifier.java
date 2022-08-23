package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.jetbrains.annotations.NotNull;

public class BleedingModifier extends BodyAttributeModifier {

  private static final double minutesUntilFullDeath = 30.0;
  private static final double damagePerMinute = 100.0 / minutesUntilFullDeath;
  private static final double damagePerSecond = damagePerMinute / 60.0;
  private static final double damagePerMillis = damagePerSecond / 1000.0;
  private static final double damagePerTicks = damagePerMillis * 50.0;

  private final int intensity;

  public BleedingModifier() {
    this(1);
  }

  public BleedingModifier(int intensity) {
    super("bleeding-effect-mod", BodyAttribute.HEALTH_SHIFT);
    this.intensity = intensity;
  }

  @Override
  public double applyAsDouble(double operand) {
    return operand - (intensity * damagePerTicks);
  }

  @Override
  public int compareTo(@NotNull BodyAttributeModifier other) {
    if(!(other instanceof BleedingModifier bleedingModifier)) {
      return super.compareTo(other);
    }
    return Integer.compare(this.intensity, bleedingModifier.intensity);
  }
}

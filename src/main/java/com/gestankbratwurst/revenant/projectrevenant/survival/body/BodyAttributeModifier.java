package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.function.DoubleUnaryOperator;

@RequiredArgsConstructor
public abstract class BodyAttributeModifier implements DoubleUnaryOperator, Comparable<BodyAttributeModifier> {

  @Getter
  private final String identifier;
  @Getter
  private final String bodyAttribute;

  @Override
  public int compareTo(@NotNull BodyAttributeModifier other) {
    return Double.compare(Math.abs(this.applyAsDouble(1.0)), Math.abs(other.applyAsDouble(1.0)));
  }

}

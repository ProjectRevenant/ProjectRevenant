package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import lombok.Getter;

public abstract class AbilityEffect<T> {

  @Getter
  private final AbilityTrigger<T> trigger;
  @Getter
  private final String identifier;

  public AbilityEffect(AbilityTrigger<T> trigger, String identifier) {
    this.trigger = trigger;
    this.identifier = identifier;
  }

  public abstract void cast(T element);

  @Override
  public int hashCode() {
    return identifier.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof AbilityEffect<?> other && identifier.equals(other.identifier);
  }
}

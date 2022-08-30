package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import lombok.Getter;

public abstract class AbilityEffect<T> {

  @Getter
  private final AbilityTrigger<T> trigger;

  public AbilityEffect(AbilityTrigger<T> trigger) {
    this.trigger = trigger;
  }

  public abstract void cast(T element);

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof AbilityEffect<?> other && getClass().equals(other.getClass());
  }
}

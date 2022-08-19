package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;

import java.time.Duration;

public abstract class TimedAbility extends Ability {

  private long endTime;

  public TimedAbility(String identifier) {
    super(identifier);
  }

  public void setDurationFromNow(Duration duration) {
    endTime = System.currentTimeMillis() + duration.toMillis();
  }

  public void appendDuration(Duration duration) {
    endTime = endTime + duration.toMillis();
  }

  public Duration getTimeLeft() {
    return Duration.ofMillis(endTime - System.currentTimeMillis());
  }

  public boolean isDone() {
    return getTimeLeft().isNegative();
  }

}

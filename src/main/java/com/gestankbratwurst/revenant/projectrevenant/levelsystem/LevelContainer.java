package com.gestankbratwurst.revenant.projectrevenant.levelsystem;

import lombok.Getter;

public class LevelContainer {

  @Getter
  private double experience = 0D;

  public void addExp(double amount) {
    experience += amount;
  }

  public void removeExp(double amount) {
    experience -= amount;
  }

  public int getCurrentLevel() {
    return LevelEvaluator.getLevelForExp(experience) + 1;
  }

  public double getTotalNextLevelExp() {
    return LevelEvaluator.getExpEnd(getCurrentLevel() - 1);
  }

  public double getTotalCurrentLevelExp() {
    return LevelEvaluator.getExpStart(getCurrentLevel() - 1);
  }

  public double getExperienceTowardsNextLevel() {
    int currentLevel = getCurrentLevel() - 1;
    return LevelEvaluator.getExpEnd(currentLevel) - LevelEvaluator.getExpStart(currentLevel);
  }

  public double getExperienceProgressTowardsNextLevel() {
    return experience - LevelEvaluator.getExpStart(getCurrentLevel() - 1);
  }

  public double getProgressPercent() {
    return Math.min(1.0, 1.0 / getExperienceTowardsNextLevel() * getExperienceProgressTowardsNextLevel());
  }

}

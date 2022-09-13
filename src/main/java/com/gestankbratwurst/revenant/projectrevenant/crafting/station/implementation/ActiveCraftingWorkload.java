package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ActiveCraftingWorkload {

  @Getter
  private final IngredientRecipe recipe;
  @Getter
  private final UUID issuer;
  private final long startTime = System.currentTimeMillis();

  public long getTimeLeft() {
    return startTime + recipe.getCraftTime().toMillis() - System.currentTimeMillis();
  }

  public boolean isDone() {
    return getTimeLeft() <= 0;
  }

  public double getProgress() {
    long time = recipe.getCraftTime().toMillis();
    return 100.0 / time * (time - getTimeLeft());
  }
}

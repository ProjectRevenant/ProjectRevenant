package com.gestankbratwurst.revenant.projectrevenant.crafting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RevenantRecipeManager {

  private final Map<UUID, RevenantRecipe> recipeMap = new HashMap<>();

  public RevenantRecipeManager() {
    Arrays.stream(BaseRecipe.values()).map(BaseRecipe::getRevenantRecipe).forEach(this::registerRecipe);
  }

  public void registerRecipe(RevenantRecipe revenantRecipe) {
    recipeMap.put(revenantRecipe.getId(), revenantRecipe);
  }

  public RevenantRecipe getRecipe(UUID recipeId) {
    return recipeMap.get(recipeId);
  }

}

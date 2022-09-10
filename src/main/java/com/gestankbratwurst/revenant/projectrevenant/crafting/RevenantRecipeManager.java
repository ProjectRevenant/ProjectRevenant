package com.gestankbratwurst.revenant.projectrevenant.crafting;

import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RevenantRecipeManager {

  private final Map<UUID, RevenantRecipe> recipeMap = new HashMap<>();

  public void registerRecipe(RevenantRecipe revenantRecipe) {
    recipeMap.put(revenantRecipe.getId(), revenantRecipe);
  }

  public RevenantRecipe getRecipe(UUID recipeId) {
    return recipeMap.get(recipeId);
  }

}

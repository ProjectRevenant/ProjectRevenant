package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.UUID;

public class FurnaceRecipe extends IngredientRecipe {

  @Getter
  private final int requiredHeatLevel;

  public FurnaceRecipe(UUID recipeId, String name, Loot result, ItemStack icon, Duration craftTime, int requiredHeatLevel) {
    super(recipeId, name, RecipeType.SMELTED, result, icon, craftTime);
    this.requiredHeatLevel = requiredHeatLevel;
  }
}

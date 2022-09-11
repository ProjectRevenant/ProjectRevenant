package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.util.common.UtilVect;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AbstractRecipeStation extends AbstractCraftingStation {

  private final Position position;
  private ActiveCraftingWorkload currentCraftingWorkload;
  private boolean workActive = false;

  public AbstractRecipeStation(Position position) {
    this.position = position;
  }

  public AbstractRecipeStation() {
    this(null);
  }

  public boolean hasGatherableLoot() {
    return currentCraftingWorkload != null && !workActive;
  }

  public void gatherLoot(Player player) {
    IngredientRecipe recipe = currentCraftingWorkload.getRecipe();
    Loot result = recipe.getResult();
    Location location = position.toLocation().add(0.5, 1, 0.5);
    result.applyTo(player, location);
    currentCraftingWorkload = null;
    workActive = false;
  }

  public void activateWorkload(IngredientRecipe ingredientRecipe, Player issuer) {
    if (isWorking()) {
      throw new IllegalStateException("Cant start a recipe while working.");
    }
    workActive = true;
    ingredientRecipe.payResources(issuer);
    currentCraftingWorkload = new ActiveCraftingWorkload(ingredientRecipe, issuer == null ? null : issuer.getUniqueId());
    super.closeAllOpenedUIs();
  }

  @Override
  public void tick() {
    if (workActive && currentCraftingWorkload != null && currentCraftingWorkload.isDone()) {
      workActive = false;
    }
    Color color;
    if(currentCraftingWorkload == null) {
      color = Color.GREEN;
    } else if(workActive) {
      color = Color.RED;
    } else {
      color = Color.AQUA;
    }
    Bukkit.getOnlinePlayers().forEach(player -> UtilVect.showBoundingBox(position.toLocation().getBlock().getBoundingBox(), player, 32, 0.1, 0.33f, color));
  }

  @Override
  public boolean isWorking() {
    return workActive;
  }

  public abstract List<IngredientRecipe> getRecipeList();
}

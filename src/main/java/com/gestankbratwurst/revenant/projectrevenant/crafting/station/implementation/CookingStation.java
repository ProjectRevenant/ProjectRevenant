package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RecipeType;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui.CookingStationUI;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.List;

public class CookingStation extends AbstractRecipeStation {

  public static final int MAX_WATER_LEVEL = 2;

  @Getter
  private int waterLevel = 0;

  public CookingStation(Position position) {
    super(position);
  }

  public CookingStation() {
    this(Position.ZERO);
  }

  public void incrementWaterLevel() {
    if (waterLevel < MAX_WATER_LEVEL) {
      waterLevel++;
    }
  }

  @Override
  public void activateWorkload(IngredientRecipe ingredientRecipe, Player issuer) {
    if(ingredientRecipe.getType() == RecipeType.COOKED) {
      if(waterLevel <= 0) {
        return;
      }
      waterLevel--;
    }
    super.activateWorkload(ingredientRecipe, issuer);
  }

  @Override
  public BlockData createBlockData() {
    return Bukkit.createBlockData(Material.SMOKER);
  }

  @Override
  public AbstractGUIInventory createUI(Player player) {
    return new CookingStationUI(this);
  }

  @Override
  public String getDisplayName() {
    return "§fKochstelle";
  }

  @Override
  protected int getEffectInterval() {
    return 10;
  }

  @Override
  protected void playEffect() {
    Location location = getPosition().toLocation().add(0.5, 1, 0.5);
    location.getWorld().playSound(location, Sound.BLOCK_CAMPFIRE_CRACKLE, 1F, 1F);
  }

  @Override
  public List<IngredientRecipe> getRecipeList() {
    return List.of((IngredientRecipe) BaseRecipe.DUMMY.getRevenantRecipe());
  }
}

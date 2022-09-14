package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui.FurnaceStationUI;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.List;

public class FurnaceStation extends AbstractRecipeStation {

  public static final int MAX_HEAT_LEVEL = 6;

  public FurnaceStation(Position position) {
    super(position);
  }

  public FurnaceStation() {
    this(Position.ZERO);
  }

  @Getter
  private int heatLevel = 0;
  @Getter
  private int burnCoolDown = 0;
  @Getter
  private int currentBurnCooldownCeiling = 1;

  public int getCoalCost() {
    return switch (heatLevel) {
      case 3, 4 -> 2;
      case 5 -> 5;
      default -> 1;
    };
  }

  public void increaseHeatLevel() {
    this.currentBurnCooldownCeiling = switch (++heatLevel) {
      case 1 -> 20 * 5;
      case 2 -> 20 * 12;
      case 3 -> 20 * 25;
      case 4 -> 20 * 60;
      case 5 -> 20 * 120;
      case 6 -> 20 * 300;
      default -> 1;
    };
    this.burnCoolDown = currentBurnCooldownCeiling;
  }

  public double getCooldownPercent() {
    return 1.0 / currentBurnCooldownCeiling * (currentBurnCooldownCeiling - burnCoolDown);
  }

  @Override
  public void tick() {
    super.tick();
    if (burnCoolDown > 0) {
      burnCoolDown--;
    }
  }

  @Override
  public BlockData createBlockData() {
    return Bukkit.createBlockData(Material.BLAST_FURNACE);
  }

  @Override
  public AbstractGUIInventory createUI(Player player) {
    return new FurnaceStationUI(this);
  }

  @Override
  public String getDisplayName() {
    return "Schmelzofen";
  }

  @Override
  protected int getEffectInterval() {
    return 20;
  }

  @Override
  protected void playEffect() {
    Location location = getPosition().toLocation().add(0.5, 0.5, 0.5);
    location.getWorld().playSound(location, Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1F, 1F);
  }

  @Override
  public List<IngredientRecipe> getRecipeList() {
    return List.of((IngredientRecipe) BaseRecipe.DUMMY.getRevenantRecipe());
  }
}

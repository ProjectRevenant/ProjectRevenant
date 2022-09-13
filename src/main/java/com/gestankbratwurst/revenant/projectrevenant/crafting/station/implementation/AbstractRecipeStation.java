package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.protocol.holograms.AbstractHologram;
import com.gestankbratwurst.core.mmcore.protocol.holograms.impl.HologramTextLine;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AbstractRecipeStation extends AbstractCraftingStation {

  @Getter
  private final Position position;
  private ActiveCraftingWorkload currentCraftingWorkload;
  private boolean workActive = false;
  private transient AbstractHologram hologram;
  private transient int ticksAlive;

  public AbstractRecipeStation(Position position) {
    this.position = position;
  }

  public AbstractRecipeStation() {
    this(Position.ZERO);
  }

  public abstract String getDisplayName();

  protected abstract int getEffectInterval();

  protected abstract void playEffect();

  public boolean hasGatherableLoot() {
    return currentCraftingWorkload != null && !workActive;
  }

  @Override
  public void onLoad() {
    this.hologram = MMCore.getHologramManager().createHologram(position.toLocation().add(0.5, 1.5, 0.5));
    hologram.appendTextLine(getDisplayName());
  }

  @Override
  public void onUnload() {
    if (hologram != null) {
      hologram.delete();
    }
  }

  public void gatherLoot(Player player) {
    IngredientRecipe recipe = currentCraftingWorkload.getRecipe();
    Loot result = recipe.getResult();
    Location location = position.toLocation().add(0.5, 1, 0.5);
    result.applyTo(player, location);
    currentCraftingWorkload = null;
    workActive = false;
    if (hologram != null) {
      ((HologramTextLine) hologram.getHologramLine(1)).update("");
    }
  }

  public void activateWorkload(IngredientRecipe ingredientRecipe, Player issuer) {
    ticksAlive = 0;
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
    if (hologram.getSize() < 2) {
      hologram.appendTextLine("");
    }
    if (workActive && currentCraftingWorkload != null && currentCraftingWorkload.isDone()) {
      workActive = false;
      ((HologramTextLine) hologram.getHologramLine(1)).update("§a[§fFertig§a]");
    }
    if (workActive && currentCraftingWorkload != null) {
      if (ticksAlive++ % getEffectInterval() == 0) {
        playEffect();
      }
      int progress = (int) currentCraftingWorkload.getProgress();
      String bar = "§f" + TextureModel.valueOf("CRAFT_PROGRESS_BAR_" + progress).getChar();
      ((HologramTextLine) hologram.getHologramLine(1)).update(bar);
    }
  }

  @Override
  public boolean isWorking() {
    return workActive;
  }

  public abstract List<IngredientRecipe> getRecipeList();
}

package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.resourcepack.sounds.CustomSound;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RecipeType;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui.CampireStationUI;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui.RecipeStationUI;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class PlayerCampfireStation extends AbstractRecipeStation {

  private static final long defaultLifetime = 60 * 1000;
  public static final long additionalLifetime = 120 * 1000;

  private long lifeEndTimestamp;

  public PlayerCampfireStation(Position position) {
    super(position);
    lifeEndTimestamp = System.currentTimeMillis() + defaultLifetime;
  }

  public PlayerCampfireStation() {
    this(Position.ZERO);
  }

  public void addLifetime(long additional) {
    lifeEndTimestamp += additional;
  }

  public Duration getLifetime(){
    return Duration.ofMillis(lifeEndTimestamp - System.currentTimeMillis());
  }

  public void setLifetimeFromNow(long time) {
    this.lifeEndTimestamp = time;
  }

  public boolean isLit() {
    Campfire campfire = (Campfire) getPosition().toLocation().getBlock().getBlockData();
    return campfire.isLit();
  }

  public void setLit(boolean value) {
    Campfire campfire = (Campfire) getPosition().toLocation().getBlock().getBlockData();
    campfire.setLit(value);
    getPosition().toLocation().getBlock().setBlockData(campfire);
  }

  @Override
  public BlockData createBlockData() {
    Campfire blockData = (Campfire) Bukkit.createBlockData(Material.CAMPFIRE);
    blockData.setLit(false);
    blockData.setSignalFire(true);
    return blockData;
  }

  @Override
  public void tick() {
    super.tick();

    if (lifeEndTimestamp <= System.currentTimeMillis()) {
      getViewers().forEach(Player::closeInventory);
      Block block = getPosition().toLocation().getBlock();
      Particle particles = Particle.BLOCK_DUST;
      block.getWorld().spawnParticle(particles, block.getLocation().add(0.5, 0.5, 0.5), 20, 0.33, 0.33, 0.33, block.getBlockData());
      block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.2f, 1.0f);
      ProjectRevenant.getCraftingStationManager().removeLoadedBlock(block, true);
    }
  }

  @Override
  public AbstractGUIInventory createUI(Player player) {
    return new CampireStationUI(this);
  }

  @Override
  public String getDisplayName() {
    return "§fLagerfeuer";
  }

  @Override
  protected int getEffectInterval() {
    return 30;
  }

  @Override
  protected void playEffect() {
    RecipeType type = currentCraftingWorkload.getRecipe().getType();
    Location location = getPosition().toLocation();
    switch (type) {
      case COOKED -> location.getWorld().playSound(location, Sound.BLOCK_SMOKER_SMOKE, SoundCategory.MASTER, 1.0f, 1.0f);
      case BAKED -> location.getWorld().playSound(location, Sound.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER, 1.0f, 1.0f);
      case FORGED -> location.getWorld().playSound(location, Sound.BLOCK_ANVIL_HIT, SoundCategory.MASTER, 1.0f, 1.0f);
      case BREWED -> location.getWorld().playSound(location, Sound.BLOCK_BREWING_STAND_BREW, SoundCategory.MASTER, 1.0f, 1.0f);
      case CRAFTED -> CustomSound.STICK_HIT_ONE.playAt(location, SoundCategory.MASTER, 1.0f, 1.0f);
    }
  }

  @Override
  public List<IngredientRecipe> getRecipeList() {
    return List.of((IngredientRecipe) BaseRecipe.DUMMY.getRevenantRecipe());
  }
}

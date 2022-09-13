package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.resourcepack.sounds.CustomSound;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui.RecipeStationUI;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DummyStation extends AbstractRecipeStation {

  public DummyStation(Position position) {
    super(position);
  }

  public DummyStation() {
    this(Position.ZERO);
  }

  @Override
  public String getDisplayName() {
    return "§fDummy Werkbank";
  }

  @Override
  protected int getEffectInterval() {
    return 20;
  }

  @Override
  protected void playEffect() {
    CustomSound sound = switch (ThreadLocalRandom.current().nextInt(3)) {
      case 0 -> CustomSound.STICK_HIT_ONE;
      case 1 -> CustomSound.STICK_HIT_TWO;
      default -> CustomSound.STICK_HIT_THREE;
    };
    sound.playAt(getPosition().toLocation().add(0.5, 0.5, 0.5), SoundCategory.PLAYERS, 0.75F, 1.0F);
  }

  @Override
  public AbstractGUIInventory createUI(Player player) {
    return new RecipeStationUI<>(this, TextureModel.DEFAULT_CRAFTING_UI);
  }

  @Override
  public List<IngredientRecipe> getRecipeList() {
    return List.of((IngredientRecipe) BaseRecipe.DUMMY.getRevenantRecipe());
  }

  @Override
  public BlockData createBlockData() {
    return Bukkit.createBlockData(Material.CRAFTING_TABLE);
  }
}

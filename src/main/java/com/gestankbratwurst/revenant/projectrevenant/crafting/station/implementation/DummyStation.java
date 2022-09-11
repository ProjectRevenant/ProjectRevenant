package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui.RecipeStationUI;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.entity.Player;

import java.util.List;

public class DummyStation extends AbstractRecipeStation {

  public DummyStation(Position position) {
    super(position);
  }

  public DummyStation() {
    this(null);
  }

  @Override
  public AbstractGUIInventory createUI(Player player) {
    return new RecipeStationUI<>(this, TextureModel.DEFAULT_CRAFTING_UI);
  }

  @Override
  public List<IngredientRecipe> getRecipeList() {
    return List.of((IngredientRecipe) BaseRecipe.DUMMY.getRevenantRecipe());
  }
}

package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.UUID;

public interface RevenantRecipe {

  UUID getId();

  Loot getResult();

  ItemStack infoIconFor(Player player);

  boolean canCraft(Player player);

  Duration getCraftTime();

  void payResources(Player player);

  String getName();

  RecipeType getType();

}

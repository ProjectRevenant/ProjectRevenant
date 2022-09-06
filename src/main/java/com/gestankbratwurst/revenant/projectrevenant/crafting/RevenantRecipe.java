package com.gestankbratwurst.revenant.projectrevenant.crafting;

import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface RevenantRecipe {

  UUID getId();

  Loot getResult();

  ItemStack infoIconFor(Player player);

  void onCraft(Player player);

  boolean canCraft(Player player);


}

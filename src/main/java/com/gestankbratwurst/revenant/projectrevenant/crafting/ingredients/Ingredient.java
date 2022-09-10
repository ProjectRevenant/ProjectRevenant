package com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public interface Ingredient extends Predicate<ItemStack> {

  Component getInfo(Player player);
  ItemStack getAsIcon(Player player);
  char getAsChar(Player player);

}

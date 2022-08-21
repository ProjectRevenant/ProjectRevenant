package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.function.Function;

@AllArgsConstructor
public class ItemLoot implements Loot {

  private final Function<Player, ItemStack> itemStackFunction;

  @Override
  public void applyTo(Player looter, Location location) {
    ItemStack item = itemStackFunction.apply(looter);
    location.getWorld().dropItemNaturally(location, item);
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {

    ItemStack item = itemStackFunction.apply(looter);
    HashMap<Integer, ItemStack> overflow = inventory.addItem(item);

    if (!overflow.isEmpty()) {
      for (ItemStack overflownItem : overflow.values()) {
        looter.getWorld().dropItemNaturally(looter.getLocation(), overflownItem);
      }
    }
  }

}

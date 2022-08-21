package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Loot {

  void applyTo(Player looter, Location location);

  void applyTo(Player looter, Inventory inventory);

}
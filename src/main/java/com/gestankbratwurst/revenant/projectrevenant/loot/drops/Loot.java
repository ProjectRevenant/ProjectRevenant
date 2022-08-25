package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Predicate;

public interface Loot {

  void applyTo(Player looter, Location location);

  void applyTo(Player looter, Inventory inventory);

  default Loot withCondition(Predicate<Player> predicate) {
    return new ConditionalLoot(predicate, this);
  }

}
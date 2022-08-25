package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Predicate;

@AllArgsConstructor
public class ConditionalLoot implements Loot {

  private final Predicate<Player> predicate;
  private final Loot delegateLoot;

  @Override
  public void applyTo(Player looter, Location location) {
    if(predicate.test(looter)) {
      delegateLoot.applyTo(looter, location);
    }
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    if(predicate.test(looter)) {
      delegateLoot.applyTo(looter, inventory);
    }
  }
}

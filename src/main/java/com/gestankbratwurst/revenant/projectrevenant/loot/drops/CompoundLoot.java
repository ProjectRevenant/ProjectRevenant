package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CompoundLoot implements Loot {

  private final List<Loot> lootList = new ArrayList<>();

  @Override
  public void applyTo(Player looter, Location location) {
    lootList.forEach(loot -> loot.applyTo(looter, location));
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    lootList.forEach(loot -> loot.applyTo(looter, inventory));
  }

  public void addLoot(Loot loot) {
    this.lootList.add(loot);
  }

}

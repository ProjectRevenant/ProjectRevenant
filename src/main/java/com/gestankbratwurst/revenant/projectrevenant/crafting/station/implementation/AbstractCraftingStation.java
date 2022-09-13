package com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStation;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractCraftingStation implements CraftingStation {

  private transient final Set<Player> viewers;

  public AbstractCraftingStation() {
    viewers = new HashSet<>();
  }

  public abstract AbstractGUIInventory createUI(Player player);

  @Override
  public void openUI(Player player) {
    if (isWorking()) {
      return;
    }
    createUI(player).openFor(player);
    viewers.add(player);
  }

  @Override
  public void unregisterViewer(Player player) {
    viewers.remove(player);
  }

  @Override
  public void closeAllOpenedUIs() {
    getViewers().forEach(HumanEntity::closeInventory);
    viewers.clear();
  }

  @Override
  public List<Player> getViewers() {
    return List.copyOf(viewers);
  }
}

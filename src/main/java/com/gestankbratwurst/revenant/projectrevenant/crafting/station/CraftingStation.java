package com.gestankbratwurst.revenant.projectrevenant.crafting.station;

import org.bukkit.entity.Player;

public interface CraftingStation {

  void openUI(Player player);

  void closeAllOpenedUIs();

  void tick();

}
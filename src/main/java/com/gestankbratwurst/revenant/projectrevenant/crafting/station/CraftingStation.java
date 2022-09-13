package com.gestankbratwurst.revenant.projectrevenant.crafting.station;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.List;

public interface CraftingStation {

  void openUI(Player player);

  void unregisterViewer(Player player);

  void closeAllOpenedUIs();

  void tick();

  boolean isWorking();

  List<Player> getViewers();

  void onLoad();

  void onUnload();

  BlockData createBlockData();

}
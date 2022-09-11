package com.gestankbratwurst.revenant.projectrevenant.crafting;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStationManager;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.AbstractRecipeStation;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Objects;

public class CraftingListener implements Listener {

  private final CraftingStationManager craftingStationManager;

  public CraftingListener() {
    craftingStationManager = ProjectRevenant.getCraftingStationManager();
  }

  @EventHandler
  public void onChunkUnload(ChunkUnloadEvent event) {
    craftingStationManager.terminateChunk(event.getChunk());
  }

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event) {
    craftingStationManager.initChunk(event.getChunk());
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    Block block = Objects.requireNonNull(event.getClickedBlock());
    CraftingStation craftingStation = craftingStationManager.getStationAt(Position.at(block));
    if (craftingStation == null) {
      return;
    }

    event.setCancelled(true);

    if (craftingStation.isWorking()) {
      return;
    }
    if (craftingStation instanceof AbstractRecipeStation recipeStation) {
      if (recipeStation.hasGatherableLoot()) {
        recipeStation.gatherLoot(event.getPlayer());
        return;
      }
    }

    craftingStation.openUI(event.getPlayer());
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    craftingStationManager.removeLoadedBlock(event.getBlock());
  }

}

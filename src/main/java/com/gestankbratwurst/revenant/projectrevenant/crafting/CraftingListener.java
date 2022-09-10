package com.gestankbratwurst.revenant.projectrevenant.crafting;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStationManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class CraftingListener implements Listener {

  private final CraftingStationManager craftingStationManager;

  public CraftingListener(){
    craftingStationManager = ProjectRevenant.getCraftingStationManager();
  }

  @EventHandler
  public void onChunkUnload(ChunkUnloadEvent event){
    craftingStationManager.terminateChunk(event.getChunk());
  }

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event){
    craftingStationManager.initChunk(event.getChunk());
  }

}

package com.gestankbratwurst.revenant.projectrevenant.spawnsystem;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.GlobalSpawnManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.NoisePolutionManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

@RequiredArgsConstructor
public class SpawnSystemListener implements Listener {

  private final NoisePolutionManager noisePolutionManager;
  private final GlobalSpawnManager globalSpawnManager;

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event) {
    noisePolutionManager.addChunk(event.getChunk());
  }

  @EventHandler
  public void onChunkUnload(ChunkUnloadEvent event){
    noisePolutionManager.removeChunk(event.getChunk());
  }

  @EventHandler
  public void onEntityRemove(EntityRemoveFromWorldEvent event){
    globalSpawnManager.removeMonsterAssignment(event.getEntity().getUniqueId());
  }

}

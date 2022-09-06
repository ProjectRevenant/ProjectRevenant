package com.gestankbratwurst.revenant.projectrevenant.spawnsystem;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.NoisePolutionManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.RevenantSpawner;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

@RequiredArgsConstructor
public class SpawnSystemListener implements Listener {

  private final NoisePolutionManager noisePolutionManager;
  private final SpawnerManager spawnerManager;

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event) {
    noisePolutionManager.addChunk(event.getChunk());
  }

  @EventHandler
  public void onChunkUnload(ChunkUnloadEvent event) {
    noisePolutionManager.removeChunk(event.getChunk());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onAddToWorld(EntityAddToWorldEvent event) {
    RevenantSpawner revenantSpawner = spawnerManager.getSpawnerOfMob(event.getEntity());
    if (revenantSpawner != null) {
      if (!revenantSpawner.isAcceptedForLoad(event.getEntity().getUniqueId())) {
        revenantSpawner.addActiveMob(event.getEntity().getUniqueId());
      } else {
        event.getEntity().getPersistentDataContainer().remove(RevenantSpawner.SPAWNER_ID_KEY);
        TaskManager.getInstance().runBukkitSync(() -> event.getEntity().remove());
      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onRemoveFromWorld(EntityRemoveFromWorldEvent event) {
    RevenantSpawner revenantSpawner = spawnerManager.getSpawnerOfMob(event.getEntity());
    if(revenantSpawner != null) {
      if(event.getEntity().isDead()) {
        revenantSpawner.removeMob(event.getEntity().getUniqueId());
      } else {
        revenantSpawner.unloadMob(event.getEntity().getUniqueId());
      }
    }
  }

}

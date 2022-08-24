package com.gestankbratwurst.revenant.projectrevenant.loot;

import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootChestManager;
import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootManager;
import lombok.AllArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.persistence.PersistentDataHolder;

@AllArgsConstructor
public class LootListener implements Listener {

  private LootChestManager lootChestManager;

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event) {
    lootChestManager.checkRespawnableChestsInChunk(event.getChunk());
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();

    if(event.getAction() != Action.RIGHT_CLICK_BLOCK){
      return;
    }

    if (block == null) {
      return;
    }

    BlockState state = block.getState();

    if (!(state instanceof BlockInventoryHolder invHolder && state instanceof PersistentDataHolder dataHolder)) {
      return;
    }

    LootManager lootManager = LootManager.getInstance();

    if (!lootManager.hasLoot(dataHolder)) {
      return;
    }

    lootManager.dropIn(invHolder.getInventory(), event.getPlayer(), dataHolder);
  }

}

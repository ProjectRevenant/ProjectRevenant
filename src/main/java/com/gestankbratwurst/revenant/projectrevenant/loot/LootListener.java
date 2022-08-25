package com.gestankbratwurst.revenant.projectrevenant.loot;

import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootChestManager;
import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootManager;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;

@AllArgsConstructor
public class LootListener implements Listener {

  private LootChestManager lootChestManager;

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent event) {
    lootChestManager.checkSpawnableChestsInChunk(event.getChunk());
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();

    if (event.getAction() != Action.RIGHT_CLICK_BLOCK || block == null) {
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

    state = block.getState();

    if (!(state instanceof PersistentDataHolder updatableDataHolder)) {
      return;
    }

    PersistentDataContainer container = updatableDataHolder.getPersistentDataContainer();
    lootManager.removeTypeFrom(container);
    lootManager.tagForRemoval(container);
    state.update(true);
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (!(event.getInventory().getHolder() instanceof BlockInventoryHolder invHolder)) {
      return;
    }

    LootManager lootManager = LootManager.getInstance();
    PersistentDataHolder dataHolder = (PersistentDataHolder) event.getInventory().getHolder();

    assert dataHolder != null;

    if (!(lootManager.isForRemoval(dataHolder.getPersistentDataContainer()))) {
      return;
    }

    Block block = invHolder.getBlock();

    block.breakNaturally();

    LootableChest lootableChest = lootChestManager.getLootableChestAt(LootableChest.Position.at(block));
    lootableChest.setRespawnTimeFromNow();
    lootChestManager.addToRespawnQueue(lootableChest);
  }

}

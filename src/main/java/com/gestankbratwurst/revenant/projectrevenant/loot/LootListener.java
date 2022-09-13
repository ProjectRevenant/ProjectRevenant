package com.gestankbratwurst.revenant.projectrevenant.loot;

import com.gestankbratwurst.core.mmcore.util.common.UtilInv;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootChestManager;
import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootManager;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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

    lootManager.renameInventory(state);

    Inventory inventory = invHolder.getInventory();
    lootManager.dropIn(inventory, event.getPlayer(), dataHolder);
    UtilInv.scramble(inventory);
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
    Inventory inventory = invHolder.getInventory();
    Location location = block.getLocation();

    for (ItemStack item : inventory) {
      if (item != null) {
        block.getWorld().dropItemNaturally(location, item);
      }
    }

    lootChestManager.reportLootedChest(Position.at(block));
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event){
    lootChestManager.removeLootChestAt(Position.at(event.getBlock()));
  }

}

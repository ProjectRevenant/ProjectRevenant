package com.gestankbratwurst.revenant.projectrevenant.metaprogression.stash;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataHolder;

public class StashListener implements Listener {

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    if (!(event.getClickedBlock().getState() instanceof PersistentDataHolder holder)) {
      return;
    }

    StashManager stashManager = ProjectRevenant.getStashManager();

    if (stashManager.isStash(holder)) {
      event.setCancelled(true);
      stashManager.openUI(event.getPlayer());
    }
  }

}

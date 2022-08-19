package com.gestankbratwurst.revenant.projectrevenant.survival.body.items;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

@RequiredArgsConstructor
public class ItemAttributeListener implements Listener {

  private final BodyManager bodyManager;

  @EventHandler
  public void onArmorChange(PlayerArmorChangeEvent event) {
    postEventCalculation(event.getPlayer());
  }

  @EventHandler
  public void onHeldChange(PlayerItemHeldEvent event) {
    postEventCalculation(event.getPlayer());
  }

  @EventHandler
  public void onItemChange(PlayerChangedMainHandEvent event) {
    postEventCalculation(event.getPlayer());
  }

  @EventHandler
  public void onItemSwap(PlayerSwapHandItemsEvent event) {
    postEventCalculation(event.getPlayer());
  }

  private void postEventCalculation(Player player) {
    TaskManager.getInstance().runBukkitSync(() -> EntityAbilityCache.autoUpdate(player, Player.class));
  }

}

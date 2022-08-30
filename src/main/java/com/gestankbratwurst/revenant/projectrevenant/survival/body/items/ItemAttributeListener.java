package com.gestankbratwurst.revenant.projectrevenant.survival.body.items;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

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

  @EventHandler
  public void onDrop(PlayerDropItemEvent event) {
    if (ItemAttributeHandler.isTwoHandedPlaceholder(event.getItemDrop().getItemStack())) {
      event.setCancelled(true);
    }
    this.postEventCalculation(event.getPlayer());
  }

  @EventHandler
  public void onDrop(EntityPickupItemEvent event) {
    if(!(event.getEntity() instanceof Player player)) {
      return;
    }
    this.postEventCalculation(player);
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    if (ItemAttributeHandler.isTwoHandedPlaceholder(event.getCurrentItem())) {
      event.setCancelled(true);
    }
  }

  private void postEventCalculation(Player player) {
    TaskManager.getInstance().runBukkitSync(() -> {
      EntityAbilityCache.autoUpdate(player, Player.class);
      checkTwoHanded(player);
    });
  }

  private void checkTwoHanded(Player player) {
    ItemStack mainHand = player.getInventory().getItemInMainHand();
    ItemStack offHand = player.getInventory().getItemInOffHand();

    if (ItemAttributeHandler.isTwoHandedPlaceholder(mainHand)) {
      player.getInventory().setItemInMainHand(null);
    }
    if (ItemAttributeHandler.isTwoHandedItem(mainHand)) {
      if (!ItemAttributeHandler.isTwoHandedPlaceholder(offHand)) {
        player.getInventory().addItem(offHand).values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
        player.getInventory().setItemInOffHand(ItemAttributeHandler.getTwoHandedPlaceholder());
      }
    } else if (ItemAttributeHandler.isTwoHandedPlaceholder(offHand)) {
      player.getInventory().setItemInOffHand(null);
    }
    if (ItemAttributeHandler.isTwoHandedItem(offHand)) {
      player.getWorld().dropItemNaturally(player.getLocation(), offHand);
      player.getInventory().setItemInOffHand(null);
    }
  }

}

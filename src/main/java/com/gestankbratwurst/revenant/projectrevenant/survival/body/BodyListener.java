package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
public class BodyListener implements Listener {

  private final BodyManager bodyManager;

  @EventHandler(priority = EventPriority.HIGH)
  public void onAdd(EntityAddToWorldEvent event) {
    if (event.getEntity() instanceof LivingEntity livingEntity && !(event.getEntity() instanceof Player)) {
      bodyManager.loadBody(livingEntity);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onRemove(EntityRemoveFromWorldEvent event) {
    if (event.getEntity() instanceof LivingEntity livingEntity && !(event.getEntity() instanceof Player)) {
      bodyManager.unloadBody(livingEntity);
    }
  }

  @EventHandler
  public void onDeath(EntityDeathEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      bodyManager.removeBody(event.getEntity());
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!bodyManager.hasBody(player)) {
      bodyManager.addBodyTo(player, new HumanBody());
    }
    bodyManager.loadBody(event.getPlayer());
    TaskManager.getInstance().runBukkitSync(() -> {
      bodyManager.getBody(player).recalculateAttributes();
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateEffects();
        userTablist.updateStatistics();
      }
    });
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onQuit(PlayerQuitEvent event) {
    bodyManager.unloadBody(event.getPlayer());
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    Player player = event.getPlayer();
    bodyManager.addBodyTo(player, new HumanBody());
    bodyManager.loadBody(player);
    TaskManager.getInstance().runBukkitSync(() -> {
      bodyManager.getBody(player).recalculateAttributes();
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateEffects();
        userTablist.updateStatistics();
      }
    });
  }

  @EventHandler
  public void onDamage(EntityDamageEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof LivingEntity livingEntity)) {
      return;
    }
    Body body = bodyManager.getBody(livingEntity);
    body.getAttribute(BodyAttribute.HEALTH).applyToCurrentValue(current -> current - event.getDamage());
    if (entity instanceof Player player) {
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateBody();
      }
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onDamageCancel(EntityDamageEvent event) {
    event.setDamage(0);
  }

  @EventHandler
  public void onHeal(EntityRegainHealthEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof LivingEntity livingEntity)) {
      return;
    }
    Body body = bodyManager.getBody(livingEntity);
    body.getAttribute(BodyAttribute.HEALTH).applyToCurrentValue(current -> current + event.getAmount());
    event.setAmount(0);
    if (entity instanceof Player player) {
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateBody();
      }
    }
  }

  @EventHandler
  public void onDrop(EntityDropItemEvent event) {
    if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
      return;
    }
    Body body = this.bodyManager.getBody(livingEntity);
    TaskManager.getInstance().runBukkitSync(body::recalculateAttributes);
  }

  @EventHandler
  public void onDrop(PlayerDropItemEvent event) {
    LivingEntity livingEntity = event.getPlayer();
    Body body = this.bodyManager.getBody(livingEntity);
    TaskManager.getInstance().runBukkitSync(body::recalculateAttributes);
  }

  @EventHandler
  public void onPickup(EntityPickupItemEvent event) {
    LivingEntity livingEntity = event.getEntity();
    Body body = this.bodyManager.getBody(livingEntity);
    TaskManager.getInstance().runBukkitSync(body::recalculateAttributes);
  }

  @EventHandler
  public void onClose(InventoryCloseEvent event) {
    LivingEntity livingEntity = event.getPlayer();
    Body body = this.bodyManager.getBody(livingEntity);
    if (body == null) {
      return;
    }
    TaskManager.getInstance().runBukkitSync(body::recalculateAttributes);
  }

  @EventHandler
  public void onSpawn(PlayerRespawnEvent event) {
    LivingEntity livingEntity = event.getPlayer();
    Body body = this.bodyManager.getBody(livingEntity);
    TaskManager.getInstance().runBukkitSync(body::recalculateAttributes);
  }

  @EventHandler
  public void onSaturationChange(FoodLevelChangeEvent event) {
    event.getEntity().setFoodLevel(10);
    event.getEntity().setSaturation(5.0F);
    event.getEntity().setExhaustion(0.0F);
    event.setCancelled(true);
  }

}
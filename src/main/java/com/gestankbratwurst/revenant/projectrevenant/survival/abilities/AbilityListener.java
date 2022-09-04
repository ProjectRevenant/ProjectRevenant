package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

@RequiredArgsConstructor
public class AbilityListener implements Listener {

  private final AbilitySecondTask abilitySecondTask;

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    abilitySecondTask.add(event.getPlayer());
    TaskManager.getInstance().runBukkitSync(() -> RevenantPlayer.of(event.getPlayer()).unpauseAbilities());
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    RevenantPlayer.of(event.getPlayer()).pauseAbilities();
    abilitySecondTask.remove(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onAdd(EntityAddToWorldEvent event) {
    EntityAbilityCache.cache(event.getEntity().getUniqueId());
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onRemove(EntityRemoveFromWorldEvent event) {
    EntityAbilityCache.uncache(event.getEntity().getUniqueId());
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onSneak(PlayerToggleSneakEvent event) {
    Player player = event.getPlayer();
    EntityAbilityCache.getAbilities(player.getUniqueId()).forEach(ability -> ability.reactOn(player, AbilityTrigger.PLAYER_SNEAK, event));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onSprint(PlayerToggleSprintEvent event) {
    Player player = event.getPlayer();
    EntityAbilityCache.getAbilities(player.getUniqueId()).forEach(ability -> ability.reactOn(player, AbilityTrigger.PLAYER_SPRINT, event));
  }

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event) {
    Entity attacker = event.getDamager();
    Entity defender = event.getEntity();

    EntityAbilityCache.getAbilities(attacker.getUniqueId()).forEach(ability -> ability.reactOn(attacker, AbilityTrigger.COMBAT_ATTACK, event));
    EntityAbilityCache.getAbilities(defender.getUniqueId()).forEach(ability -> ability.reactOn(defender, AbilityTrigger.COMBAT_DEFEND, event));
  }

  //ToDo only trigger abilities of consumed item
  @EventHandler
  public void onConsume(PlayerItemConsumeEvent event) {
    EntityAbilityCache.getAbilities(event.getPlayer().getUniqueId()).forEach(ability -> ability.reactOn(event.getPlayer(), AbilityTrigger.CONSUME_ITEM, event));
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    EntityAbilityCache.getAbilities(event.getPlayer().getUniqueId()).forEach(ability -> ability.reactOn(event.getPlayer(), AbilityTrigger.PLAYER_INTERACT, event));
  }

}

package com.gestankbratwurst.revenant.projectrevenant.combatlog;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatLogListener implements Listener {

  private final CombatLogManager manager;

  public CombatLogListener(CombatLogManager manager) {
    this.manager = manager;
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    RevenantPlayer.of(player).setLogoutInventory(player.getInventory().getContents());

    manager.playerQuit(player);
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    LivingEntity deadEntity = event.getEntity();
    if (deadEntity instanceof Player) {
      return;
    }

    manager.playerReplacementDeath(deadEntity);
  }

}

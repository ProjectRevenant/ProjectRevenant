package com.gestankbratwurst.revenant.projectrevenant.data.player;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.overweight.OverweightDebuff;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ReventantPlayerListener implements Listener {

  private final RevenantPlayerManager revenantPlayerManager;

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onLogin(AsyncPlayerPreLoginEvent event) {
    if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
      return;
    }
    revenantPlayerManager.loadData(event.getUniqueId());
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onJoin(PlayerJoinEvent event) {
    TaskManager.getInstance().runBukkitSync(() -> revenantPlayerManager.getOnline(event.getPlayer().getUniqueId()).addAbility(new OverweightDebuff()));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onQuit(PlayerQuitEvent event) {
    TaskManager.getInstance().runBukkitAsync(() -> revenantPlayerManager.unloadData(event.getPlayer().getUniqueId()));
  }

}

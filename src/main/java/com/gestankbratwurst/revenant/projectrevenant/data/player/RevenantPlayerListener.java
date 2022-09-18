package com.gestankbratwurst.revenant.projectrevenant.data.player;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.score.ScoreType;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.overweight.OverweightDebuff;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
public class RevenantPlayerListener implements Listener {

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
    RevenantPlayer revenantPlayer = revenantPlayerManager.getOnline(event.getPlayer().getUniqueId());
    TaskManager.getInstance().runBukkitSync(() -> {
      revenantPlayer.addAbility(new OverweightDebuff());
      revenantPlayer.ensurePerkIntegrity();
    });
    revenantPlayer.setJoinTimestamp(System.currentTimeMillis());
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onRespawn(PlayerRespawnEvent event) {
    RevenantPlayer revenantPlayer = revenantPlayerManager.getOnline(event.getPlayer().getUniqueId());
    TaskManager.getInstance().runBukkitSync(revenantPlayer::ensurePerkIntegrity);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onQuit(PlayerQuitEvent event) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(event.getPlayer());
    if (!(revenantPlayer.isInLobby() || revenantPlayer.isInSpawnPod())) {
      revenantPlayer.addSurvivalTime(System.currentTimeMillis() - revenantPlayer.getJoinTimestamp());
    }
    TaskManager.getInstance().runBukkitAsync(() -> revenantPlayerManager.unloadData(event.getPlayer().getUniqueId()));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerDeath(PlayerDeathEvent event) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(event.getPlayer());
    revenantPlayer.addSurvivalTime(System.currentTimeMillis() - revenantPlayer.getJoinTimestamp());
    revenantPlayer.addScore(ScoreType.SURVIVED_TIME, (int) revenantPlayer.getSurvivalTime() / 10000);
    revenantPlayer.setJoinTimestamp(System.currentTimeMillis());

    ProjectRevenant.getMetaProgressionManager().reportPlayerDeath(event.getPlayer());
  }

}

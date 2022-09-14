package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.player;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnListener implements Listener {

  public static final String SPAWN_POD_TAG = "IN_SPAWN_POD";

  @EventHandler
  public void onSpawn(PlayerSpawnLocationEvent event) {
    Position position = ProjectRevenant.getPlayerSpawnManager().getRandomSpawnPod();
    if (position == null) {
      return;
    }
    event.getPlayer().addScoreboardTag(SPAWN_POD_TAG);
    event.setSpawnLocation(position.toLocation());
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (!event.getPlayer().getScoreboardTags().contains(SPAWN_POD_TAG)) {
      RevenantPlayer.of(event.getPlayer()).setLogoutPosition(Position.at(event.getPlayer().getLocation()));
    }
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    Position position = ProjectRevenant.getPlayerSpawnManager().getRandomSpawnPoint();
    if (position == null) {
      return;
    }
    event.setRespawnLocation(position.toLocation());
  }

  @EventHandler
  public void onResourcepackAccept(PlayerResourcePackStatusEvent event) {
    if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
      Position position = RevenantPlayer.of(event.getPlayer()).getLogoutPosition();
      if (position == null) {
        position = ProjectRevenant.getPlayerSpawnManager().getRandomSpawnPoint();
      }
      if (position == null) {
        return;
      }
      Position finalPosition = position;
      TaskManager.getInstance().runBukkitSync(() -> {
        event.getPlayer().removeScoreboardTag(SPAWN_POD_TAG);
        event.getPlayer().teleport(finalPosition.toLocation());
        TaskManager.getInstance().runBukkitSync(() -> event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F));
      });
    }
  }

}

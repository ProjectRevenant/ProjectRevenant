package com.gestankbratwurst.revenant.projectrevenant.ui.tab;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class TabListListener implements Listener {

  private final TabListTask tabListTask;

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    tabListTask.add(event.getPlayer());
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    tabListTask.remove(event.getPlayer());
  }

}

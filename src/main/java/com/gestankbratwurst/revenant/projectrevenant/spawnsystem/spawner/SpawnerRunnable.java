package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpawnerRunnable implements Runnable {

  private final SpawnerManager spawnerManager;

  @Override
  public void run() {
    spawnerManager.tickSpawners();
  }
}

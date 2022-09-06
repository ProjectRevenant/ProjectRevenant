package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

public class GlobalSpawnTask implements Runnable {

  private final GlobalSpawnManager globalSpawnManager;

  public GlobalSpawnTask(GlobalSpawnManager globalSpawnManager){
    this.globalSpawnManager = globalSpawnManager;
  }

  @Override
  public void run() {
    globalSpawnManager.spawnForNext();
  }

}

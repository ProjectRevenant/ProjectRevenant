package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

public class ChunkHeatTask implements Runnable {

  private final ChunkHeatManager chunkHeatManager;

  public ChunkHeatTask(ChunkHeatManager chunkHeatManager){
    this.chunkHeatManager = chunkHeatManager;
  }

  @Override
  public void run() {
    chunkHeatManager.updateChunkNoise();
  }

}

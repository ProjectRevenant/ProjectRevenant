package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

public class NoisePolutionTask implements Runnable {

  private final NoisePolutionManager noisePolutionManager;

  public NoisePolutionTask(NoisePolutionManager noisePolutionManager){
    this.noisePolutionManager = noisePolutionManager;
  }

  @Override
  public void run() {
    noisePolutionManager.updateChunkNoise();
  }

}

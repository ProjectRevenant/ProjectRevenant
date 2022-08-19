package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BodyRunnable implements Runnable {

  private final BodyManager bodyManager;

  @Override
  public void run() {
    this.bodyManager.tickLiveBodies();
  }
}

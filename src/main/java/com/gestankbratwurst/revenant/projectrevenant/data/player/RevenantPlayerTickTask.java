package com.gestankbratwurst.revenant.projectrevenant.data.player;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevenantPlayerTickTask implements Runnable {

  private final RevenantPlayerManager revenantPlayerManager;

  @Override
  public void run() {
    revenantPlayerManager.forEach(RevenantPlayer::tick);
  }
}

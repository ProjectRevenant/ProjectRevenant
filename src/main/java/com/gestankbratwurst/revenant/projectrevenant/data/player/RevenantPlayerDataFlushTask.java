package com.gestankbratwurst.revenant.projectrevenant.data.player;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevenantPlayerDataFlushTask implements Runnable {

  public static final int SECONDS_BETWEEN_SAVES = 900;
  public static final int TICKS_BETWEEN_SAVES = SECONDS_BETWEEN_SAVES * 900;

  private final RevenantPlayerManager revenantPlayerManager;

  @Override
  public void run() {
    revenantPlayerManager.flush();
  }
}

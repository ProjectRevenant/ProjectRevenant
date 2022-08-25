package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import java.util.concurrent.ThreadLocalRandom;

public class ChanceLoot extends ConditionalLoot {
  public ChanceLoot(double chance, Loot delegateLoot) {
    super((player -> ThreadLocalRandom.current().nextDouble() <= chance), delegateLoot);
  }
}

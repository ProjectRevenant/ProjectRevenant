package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

public class SimpleExpLoot extends ExpLoot {

  public SimpleExpLoot(int amount) {
    super((player) -> (amount));
  }

}

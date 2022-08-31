package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wounds.WoundInfectionDebuff;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BleedingInfectionEffect extends AbilityEffect<Player> {
  public BleedingInfectionEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND);
  }

  @Override
  public void cast(Player element) {
    if(ThreadLocalRandom.current().nextDouble() < 0.001) {
      RevenantPlayer.of(element).addAbility(new WoundInfectionDebuff());
    }
  }
}

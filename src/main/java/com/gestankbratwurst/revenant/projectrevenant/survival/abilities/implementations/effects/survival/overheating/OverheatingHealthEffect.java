package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overheating;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class OverheatingHealthEffect extends AbilityEffect<Player> {
  public OverheatingHealthEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, "overheating-debuff-damage");
  }


  @Override
  public void cast(Player element) {
    BodyAttribute health = RevenantPlayer.of(element).getBody().getAttribute(BodyAttribute.HEALTH);
    if(ThreadLocalRandom.current().nextDouble() < 0.2) {
      health.applyToCurrentValue(current -> current - 0.01 * health.getMaxValueModified());
      element.damage(0);
    }
  }
}

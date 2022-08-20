package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BrokenBoneDamageEffect extends AbilityEffect<Player> {

  private static final double TRIGGER_CHANCE = 0.075;

  public BrokenBoneDamageEffect(String identifier) {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, identifier);
  }

  public BrokenBoneDamageEffect() {
    this("broken-bone");
  }

  @Override
  public void cast(Player element) {
    if(ThreadLocalRandom.current().nextDouble() > TRIGGER_CHANCE) {
      return;
    }
    RevenantPlayer.of(element).getBody().getAttribute(BodyAttribute.HEALTH).applyToCurrentValue(current -> current - 5);
    element.damage(0);
  }
}

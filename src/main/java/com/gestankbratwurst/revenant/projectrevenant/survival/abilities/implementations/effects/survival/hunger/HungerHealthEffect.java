package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.hunger;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.entity.Player;

public class HungerHealthEffect extends AbilityEffect<Player> {
  public HungerHealthEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, "hunger-debuff-damage");
  }


  @Override
  public void cast(Player element) {
    BodyAttribute health = RevenantPlayer.of(element).getBody().getAttribute(BodyAttribute.HEALTH);
    health.applyToCurrentValue(current -> current - 0.02 * health.getMaxValueModified());
    element.damage(0);
  }
}

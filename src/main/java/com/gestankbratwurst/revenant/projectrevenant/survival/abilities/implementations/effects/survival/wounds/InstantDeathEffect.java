package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import lombok.Getter;
import org.bukkit.entity.Player;

public class InstantDeathEffect extends AbilityEffect<Player> {

  public InstantDeathEffect() {
    this(System.currentTimeMillis());
  }

  public InstantDeathEffect(long deathTime) {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, "instant-death-effect");
    this.deathTime = deathTime;
  }

  @Getter
  private final long deathTime;

  @Override
  public void cast(Player element) {
    if(System.currentTimeMillis() > deathTime) {
      RevenantPlayer.of(element).getBody().getAttribute(BodyAttribute.HEALTH).setCurrentValueUnsafe(1);
      element.damage(1000000);
    }
  }
}

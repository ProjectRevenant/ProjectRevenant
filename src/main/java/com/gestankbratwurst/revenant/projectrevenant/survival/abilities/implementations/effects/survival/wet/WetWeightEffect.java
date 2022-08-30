package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wet;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wet.WetDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WetWeightEffect extends AbilityEffect<Body> {
  public WetWeightEffect() {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "wet-weight-effect");
  }

  @Override
  public void cast(Body element) {
    Player player = Bukkit.getPlayer(element.getEntityId());
    if (player == null) {
      return;
    }
    WetDebuff wetDebuff = RevenantPlayer.of(player).getAbility(WetDebuff.class);
    double kilos = wetDebuff.getLitres();
    BodyAttribute weightAttribute = element.getAttribute(BodyAttribute.WEIGHT);
    weightAttribute.setCurrentValueUnsafe(weightAttribute.getCurrentValue() + kilos);
  }
}

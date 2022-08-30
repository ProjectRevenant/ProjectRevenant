package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.SaltPoisoningAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.time.Duration;

public class SaltyBottleDrinkEffect extends AbilityEffect<PlayerItemConsumeEvent> {
  public SaltyBottleDrinkEffect() {
    super(AbilityTrigger.CONSUME_ITEM, "salty-bottle-drink");
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    element.setReplacement(RevenantItem.emptyWaterBottle());
    SaltPoisoningAbility ability = new SaltPoisoningAbility();
    ability.setDurationFromNow(Duration.ofSeconds(120));
    RevenantPlayer.of(element.getPlayer()).addAbility(ability);
  }
}

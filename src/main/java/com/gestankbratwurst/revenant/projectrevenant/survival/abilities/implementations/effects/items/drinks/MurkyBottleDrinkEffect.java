package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.FoodPoisoningDebuff;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class MurkyBottleDrinkEffect extends AbilityEffect<PlayerItemConsumeEvent> {
  public MurkyBottleDrinkEffect() {
    super(AbilityTrigger.CONSUME_ITEM);
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    if (ThreadLocalRandom.current().nextDouble() <= 0.4) {
      RevenantPlayer revenantPlayer = RevenantPlayer.of(element.getPlayer());
      if (revenantPlayer.hasAbility(FoodPoisoningDebuff.class)) {
        FoodPoisoningDebuff ability = revenantPlayer.getAbility(FoodPoisoningDebuff.class);
        ability.appendDuration(Duration.ofSeconds(120));
      } else {
        FoodPoisoningDebuff ability = new FoodPoisoningDebuff();
        ability.setDurationFromNow(Duration.ofSeconds(90));
        revenantPlayer.addAbility(ability);
      }
    }
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.murkywaterbottle;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.foodpoisoning.FoodPoisoningAbility;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class MurkyBottleDrinkEffect extends AbilityEffect<PlayerItemConsumeEvent> {
  public MurkyBottleDrinkEffect() {
    super(AbilityTrigger.CONSUME_ITEM, "murky-bottle-drink");
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    if (ThreadLocalRandom.current().nextDouble() <= 0.4) {
      RevenantPlayer revenantPlayer = RevenantPlayer.of(element.getPlayer());
      if (revenantPlayer.hasAbility(RevenantAbility.FOOD_POISONING)) {
        FoodPoisoningAbility ability = (FoodPoisoningAbility) revenantPlayer.getAbility(RevenantAbility.FOOD_POISONING);
        ability.appendDuration(Duration.ofSeconds(120));
      } else {
        FoodPoisoningAbility ability = new FoodPoisoningAbility();
        ability.setDurationFromNow(Duration.ofSeconds(90));
        revenantPlayer.addAbility(ability);
      }
    }
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.clearwaterbottle;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.thirstrecovery.ThirstRecoveryAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.time.Duration;

public class ClearWaterDrinkEffect extends AbilityEffect<PlayerItemConsumeEvent> {
  public ClearWaterDrinkEffect() {
    super(AbilityTrigger.CONSUME_ITEM, "clear-water-drink-effect");
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    element.setReplacement(RevenantItem.emptyWaterBottle());
    RevenantPlayer revenantPlayer = RevenantPlayer.of(element.getPlayer());
    if (revenantPlayer.hasAbility(RevenantAbility.THIRST_RECOVERY)) {
      ThirstRecoveryAbility ability = (ThirstRecoveryAbility) revenantPlayer.getAbility(RevenantAbility.THIRST_RECOVERY);
      if (ability.isDone()) {
        ability.setDurationFromNow(Duration.ofSeconds(10));
      } else {
        if (ability.getTimeLeft().compareTo(Duration.ofSeconds(30)) <= 0) {
          ability.appendDuration(Duration.ofSeconds(10));
        }
      }
    } else {
      ThirstRecoveryAbility ability = new ThirstRecoveryAbility();
      ability.setDurationFromNow(Duration.ofSeconds(10));
      revenantPlayer.addAbility(ability);
    }
  }
}

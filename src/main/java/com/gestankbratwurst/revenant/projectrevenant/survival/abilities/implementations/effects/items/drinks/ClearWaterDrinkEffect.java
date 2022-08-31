package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.ThirstRecoveryBuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ClearWaterDrinkEffect extends AbilityEffect<PlayerItemConsumeEvent> {
  public ClearWaterDrinkEffect() {
    super(AbilityTrigger.CONSUME_ITEM);
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    element.setReplacement(RevenantItem.emptyWaterBottle());
    RevenantPlayer revenantPlayer = RevenantPlayer.of(element.getPlayer());

    ThirstRecoveryBuff ability = new ThirstRecoveryBuff(1.0);
    revenantPlayer.addAbility(ability);

  }
}
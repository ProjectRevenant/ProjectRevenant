package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.ConsumableHealthBuff;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumableHealthTrigger extends AbilityEffect<PlayerItemConsumeEvent> {

  @Getter
  public final ConsumableHealthBuff buff;
  public ConsumableHealthTrigger(ConsumableHealthBuff buff){
    super(AbilityTrigger.CONSUME_ITEM);
    this.buff = buff;
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    buff.startEffect();
    RevenantPlayer.of(element.getPlayer()).addAbility(buff);
  }
}

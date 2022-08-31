package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.ConsumablePotionBuff;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumablePotionTriggerEffect extends AbilityEffect<PlayerItemConsumeEvent> {

    private final ConsumablePotionBuff buff;

    public ConsumablePotionTriggerEffect(ConsumablePotionBuff buff){
        super(AbilityTrigger.CONSUME_ITEM);
        this.buff = buff;
    }


    @Override
    public void cast(PlayerItemConsumeEvent element) {
        RevenantPlayer.of(element.getPlayer()).addAbility(buff);
    }
}

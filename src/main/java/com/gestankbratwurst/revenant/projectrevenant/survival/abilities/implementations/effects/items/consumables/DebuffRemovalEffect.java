package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class DebuffRemovalEffect extends AbilityEffect<PlayerItemConsumeEvent> {

    private final Ability removeAbility;

    public DebuffRemovalEffect(Ability removeAbility) {
        super(AbilityTrigger.CONSUME_ITEM);
        this.removeAbility = removeAbility;
    }

    @Override
    public void cast(PlayerItemConsumeEvent element) {
        RevenantPlayer player = RevenantPlayer.of(element.getPlayer());
        if(player.hasAbility(removeAbility)){
            player.removeAbility(removeAbility);
        }
    }
}

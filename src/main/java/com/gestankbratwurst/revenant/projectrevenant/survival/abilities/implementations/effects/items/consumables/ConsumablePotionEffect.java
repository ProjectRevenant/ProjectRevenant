package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ConsumablePotionEffect extends AbilityEffect<Player> {

    @Getter
    private final PotionEffect potionEffect;

    public ConsumablePotionEffect(PotionEffect potionEffect){
        super(AbilityTrigger.PLAYER_EVERY_SECOND);
        this.potionEffect = potionEffect;
    }

    @Override
    public void cast(Player element) {
        element.addPotionEffect(potionEffect.withDuration(30));
    }
}

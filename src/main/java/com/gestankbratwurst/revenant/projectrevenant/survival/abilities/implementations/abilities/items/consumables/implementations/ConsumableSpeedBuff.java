package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.implementations;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.ConsumablePotionBuff;
import org.bukkit.potion.PotionEffect;

public class ConsumableSpeedBuff extends ConsumablePotionBuff {
    public ConsumableSpeedBuff(PotionEffect effect) {
        super(effect);
    }

    @Override
    public String getPlainTextName() {
        return "Geschwindigkeit";
    }
}

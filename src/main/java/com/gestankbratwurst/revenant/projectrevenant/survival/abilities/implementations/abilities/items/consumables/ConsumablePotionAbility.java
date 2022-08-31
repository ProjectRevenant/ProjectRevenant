package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumablePotionTriggerEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ConsumablePotionAbility extends Ability {

    private final ConsumablePotionBuff buff;

    public ConsumablePotionAbility(ConsumablePotionBuff buff){
        this.addEffect(new ConsumablePotionTriggerEffect(buff));
        this.buff = buff;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return false;
    }

    @Override
    public boolean shouldDisplayInActionbar() {
        return false;
    }

    @Override
    public Component getInfoTitle(Player viewer) {
        return Component.text("§9Potion-Effekt");
    }

    @Override
    public List<Component> getInfos(Player viewer) {
        List<Component> output = new ArrayList<>();
        PotionEffect effect = buff.getPotionEffect();
        int timeLeft = effect.getDuration() / 20;
        String typeName = effect.getType().getName();
        String amplifier = effect.getAmplifier() + "";

        if(timeLeft < 60){
            output.add(Component.text(String.format("§e%s %s-Effekt für %ds", buff.getPlainTextName(), amplifier, timeLeft)));
        } else {
            output.add(Component.text(String.format("§e%s %s-Effekt für %dmin", typeName, amplifier, (timeLeft / 60))));
        }

        return output;
    }

    @Override
    public TextureModel getModel() {
        return TextureModel.RED_X;
    }

    @Override
    public String getPlainTextName() {
        return "Potion-Effekt";
    }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumablePotionEffect;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.List;

public abstract class ConsumablePotionBuff extends TimedAbility implements Mergeable<ConsumablePotionBuff> {

    @Getter
    private final PotionEffect potionEffect;

    public ConsumablePotionBuff() {
        this(new PotionEffect(PotionEffectType.SPEED, 0, 1, false, false, false));
    }

    public ConsumablePotionBuff(PotionEffect effect) {
        this.potionEffect = effect;
        Duration duration = Duration.ofSeconds(effect.getDuration() / 20);
        this.addEffect(new ConsumablePotionEffect(effect));
        this.setDurationFromNow(duration);
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public boolean shouldDisplayInActionbar() {
        return false;
    }

    @Override
    public Component getInfoTitle(Player viewer) {
        Duration timeLeft = this.getTimeLeft();
        if (timeLeft.getSeconds() <= 60) {
            return Component.text("§9Potion-Effekt (" + timeLeft.getSeconds() + "s)");
        }

        return Component.text("§9Potion-Effekt (" + this.getTimeLeft() + "m)");
    }

    @Override
    public List<Component> getInfos(Player viewer) {
        return List.of();
    }

    @Override
    public TextureModel getModel() {
        return TextureModel.RED_X;
    }


    @Override
    public void merge(ConsumablePotionBuff other) {
        Duration timeLeft = other.getTimeLeft();
        this.setDurationFromNow(this.getTimeLeft().plus(timeLeft));
    }


}

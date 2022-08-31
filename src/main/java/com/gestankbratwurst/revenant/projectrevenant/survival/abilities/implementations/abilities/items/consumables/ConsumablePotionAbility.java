package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumablePotionTriggerEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class ConsumablePotionAbility extends Ability {

    public ConsumablePotionAbility(ConsumablePotionBuff buff){
        this.addEffect(new ConsumablePotionTriggerEffect(buff));
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
        return Component.text("");
    }

    @Override
    public List<Component> getInfos(Player viewer) {
        return List.of();
    }

    @Override
    public TextureModel getModel() {
        return TextureModel.RED_X;
    }
}

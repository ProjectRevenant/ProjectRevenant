package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.DebuffRemovalEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DebuffRemovalAbility extends Ability {

    private final List<String> componentStrings;

    public DebuffRemovalAbility(){
        this(List.of());
    }

    public DebuffRemovalAbility(List<Ability> remove){
        this.componentStrings = new ArrayList<>();

        for(Ability toRemove : remove) {
            this.addEffect(new DebuffRemovalEffect(toRemove));
            componentStrings.add(String.format("§7Entfernt den §e%s § Effekt", toRemove.getPlainTextName()));
        }

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
        return Component.text("§9Heilt");
    }


    @Override
    public List<Component> getInfos(Player viewer) {
        List<Component> output = new ArrayList<>();

        for(String effect : componentStrings){
            output.add(Component.text(effect));
        }

        return output;
    }

    @Override
    public String getPlainTextName() {
        return "Debuff Entfernung";
    }

    @Override
    public TextureModel getModel() {
        return TextureModel.RED_X;
    }
}

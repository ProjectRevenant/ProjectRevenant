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

    private final Ability[] remove;

    public DebuffRemovalAbility(Ability[] remove){
        for(Ability toRemove : remove) {
            this.addEffect(new DebuffRemovalEffect(toRemove));
        }
        this.remove = remove;
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

        for (Ability ability : remove){
            output.add(Component.text(String.format("§7Entfernt den §e%s § Effekt", ability.getInfoTitle(viewer))));
        }
        
        return output;
    }

    @Override
    public TextureModel getModel() {
        return TextureModel.RED_X;
    }
}

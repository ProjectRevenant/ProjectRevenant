package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks.PerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.perks.saveenergy.SaveEnergyEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class SaveHungerPerkAbility extends PerkAbility {

  public SaveHungerPerkAbility() {
    this.addEffect(new SaveEnergyEffect());
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
    return Component.text("§aPerk: §7Sparsamer Energieverbrauch");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du verbrauchst §e10% §7weniger Energie.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Sparsamer Energieverbrauch";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

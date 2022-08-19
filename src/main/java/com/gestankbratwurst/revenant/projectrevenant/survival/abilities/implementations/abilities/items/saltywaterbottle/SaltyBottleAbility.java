package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.saltywaterbottle;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.saltybottle.SaltyBottleDrinkEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class SaltyBottleAbility extends Ability {
  public SaltyBottleAbility() {
    super(RevenantAbility.SALTY_BOTTLE_DRINK);
    this.addEffect(new SaltyBottleDrinkEffect());
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
    return Component.text("§fTrinkbar");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Enthält §e1L §7salziges Wasser.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

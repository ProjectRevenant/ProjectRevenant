package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor.ArmorEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class ArmorAbility extends Ability {

  private final double armor;

  public ArmorAbility(double armor, String sufix) {
    this.armor = armor;
    this.addEffect(new ArmorEffect(armor, sufix));
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
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text("§9Rüstung");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text(String.format("§7Hat §e%.1f §7Physische Rüstung", armor))
    );
  }

}

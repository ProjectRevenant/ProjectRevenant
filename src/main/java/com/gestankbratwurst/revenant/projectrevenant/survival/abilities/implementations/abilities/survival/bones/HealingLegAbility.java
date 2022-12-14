package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.PercentageSpeedEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class HealingLegAbility extends Ability {
  public HealingLegAbility() {
    this.addEffect(new PercentageSpeedEffect(0.9, "healing-leg"));
  }

  @Override
  public boolean shouldDisplayInTab() {
    return true;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return true;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.HEALING_BONE_SMALL.getChar() + " §6Beinschiene");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Dein Lauftempo ist um §c15% §7verringert.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Beinschiene";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.HEALING_BONE;
  }
}

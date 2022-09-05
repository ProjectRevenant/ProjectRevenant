package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.PercentageMaxWeightEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class HealingRibsAbility extends Ability {
  public HealingRibsAbility() {
    this.addEffect(new PercentageMaxWeightEffect(0.85, "healing-ribs"));
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
    return Component.text(TextureModel.TORSO_HEALING_BONE_SMALL.getChar() + " §6Torsoschiene");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine Tragekraft ist um §c15% §7verringert.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Toroschiene";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.TORSO_HEALING_BONE;
  }
}

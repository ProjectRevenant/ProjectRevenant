package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.HealthLossEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.PercentageMaxWeightEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenRibsAbility extends Ability {
  public BrokenRibsAbility() {
    this.addEffect(new PercentageMaxWeightEffect(0.6, "broken-ribs"));
    this.addEffect(new HealthLossEffect(0.00025, "broken-rib"));
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
    return Component.text(TextureModel.TORSO_BROKEN_BONE.getChar() + " §cRippenbruch");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine Tragekraft ist um §c40% §7verringert."),
            Component.text("§7Außerdem verlierst du langsam Leben.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Rippenbruch";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.TORSO_BROKEN_BONE;
  }
}

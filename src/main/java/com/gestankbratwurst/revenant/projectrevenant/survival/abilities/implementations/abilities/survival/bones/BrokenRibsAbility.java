package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.BrokenBoneDamageEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.ribs.BrokenRibsEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenRibsAbility extends Ability {
  public BrokenRibsAbility() {
    this.addEffect(new BrokenRibsEffect());
    this.addEffect(new BrokenBoneDamageEffect("broken-ribs-damage-effect"));
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

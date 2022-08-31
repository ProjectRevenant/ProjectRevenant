package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.BrokenBoneDamageEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.arm.BrokenArmEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenArmAbility extends Ability {
  public BrokenArmAbility() {
    this.addEffect(new BrokenArmEffect());
    this.addEffect(new BrokenBoneDamageEffect("broken-arm-damage-effect"));
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
    return Component.text(TextureModel.BROKEN_BONE_SMALL.getChar() + " §cArmbruch");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Dein Nahkampf ist um §c40% §7verringert."),
            Component.text("§7Außerdem verlierst du langsam Leben.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Armbruch";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.BROKEN_BONE;
  }
}

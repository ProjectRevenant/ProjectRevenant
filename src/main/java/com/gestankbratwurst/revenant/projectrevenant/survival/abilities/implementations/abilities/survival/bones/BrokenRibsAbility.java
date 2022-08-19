package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenRibsAbility extends Ability {
  public BrokenRibsAbility() {
    super(RevenantAbility.BROKEN_RIBS);
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
            Component.text("§7Dein Schaden ist um §c20% §7verringert."),
            Component.text("§7Dein Lauftempo ist um §c20% §7verringert."),
            Component.text("§7Deine Tragekraft ist um §c20% §7verringert."),
            Component.text("§7Außerdem verlierst du langsam Leben.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.TORSO_BROKEN_BONE;
  }
}

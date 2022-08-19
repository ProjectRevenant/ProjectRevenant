package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenLegAbility extends Ability {
  public BrokenLegAbility() {
    super(RevenantAbility.BROKEN_LEG);
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
    return Component.text(TextureModel.BROKEN_BONE_SMALL.getChar() + " §cBeinbruch");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine Laufgeschwindigkeit ist um"),
            Component.text("§c40% §7verringert. Außerdem verlierst"),
            Component.text("§7du langsam Leben.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.BROKEN_BONE;
  }
}

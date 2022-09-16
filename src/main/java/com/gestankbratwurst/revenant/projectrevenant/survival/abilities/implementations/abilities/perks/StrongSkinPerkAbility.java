package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks.PerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.perks.strongskin.StrongSkinEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class StrongSkinPerkAbility extends PerkAbility {

  public StrongSkinPerkAbility() {
    this.addEffect(new StrongSkinEffect());
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
    return Component.text("§aPerk: §7Widerstandsfähig");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine maximalen Lebenspunkte"),
            Component.text("§7sind um §e10% §7 erhöht.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Widerstandsfähig";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

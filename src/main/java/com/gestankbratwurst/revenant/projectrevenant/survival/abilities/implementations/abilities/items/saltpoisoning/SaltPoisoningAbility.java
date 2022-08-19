package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.saltpoisoning;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.saltybottle.SaltyPoisoningEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class SaltPoisoningAbility extends TimedAbility {
  public SaltPoisoningAbility() {
    super(RevenantAbility.SALT_POISONING);
    this.addEffect(new SaltyPoisoningEffect());
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
    return Component.text( TextureModel.SALT_POISONING_SMALL + " §cSalzvergiftung");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deinem Körper wird langsam Wasser"),
            Component.text("§7und Leben entzogen. Außerdem ist"),
            Component.text("§7Dein Nahkampfschaden um §c10% §7verringer.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.SALT_POISONING;
  }

}

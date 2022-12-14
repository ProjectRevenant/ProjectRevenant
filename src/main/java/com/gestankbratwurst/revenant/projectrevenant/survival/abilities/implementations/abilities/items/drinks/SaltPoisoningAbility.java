package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.SaltyPoisoningEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class SaltPoisoningAbility extends TimedAbility {
  public SaltPoisoningAbility() {
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
    return Component.text(TextureModel.SALT_POISONING_SMALL.getChar() + " §cSalzvergiftung");
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
  public String getPlainTextName() {
    return "Salzvergiftung";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.SALT_POISONING;
  }

}

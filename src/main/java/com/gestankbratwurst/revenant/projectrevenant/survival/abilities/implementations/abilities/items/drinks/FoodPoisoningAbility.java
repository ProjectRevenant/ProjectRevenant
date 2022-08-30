package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.FoodPoisoningEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class FoodPoisoningAbility extends TimedAbility {
  public FoodPoisoningAbility() {
    super(RevenantAbility.FOOD_POISONING);
    this.addEffect(new FoodPoisoningEffect());
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
    return Component.text(TextureModel.FOOD_POISONING_SMALL.getChar() + " §cLebensmittelvergiftung");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du verlierst langsam Nahrung"),
            Component.text("§7und Leben. Außerdem ist deine"),
            Component.text("§7Laufgeschwindigkeit um §c10% §7verringert.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.FOOD_POISONING;
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.undercooling;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.HealthLossEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.undercooling.UndercoolingNutritionEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class UndercoolingDebuff extends Ability {
  public UndercoolingDebuff() {
    this.addEffect(new HealthLossEffect(0.002, "undercooling"));
    this.addEffect(new UndercoolingNutritionEffect());
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
    return Component.text(TextureModel.UNDERCOOL_DEBUFF_SMALL.getChar() + " §cHypothermie");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du verbrauchst §cviermal§7 so viel"),
            Component.text("§7Nahrung und verlierst langsam Leben.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Hypothermie";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.UNDERCOOL_DEBUFF;
  }
}

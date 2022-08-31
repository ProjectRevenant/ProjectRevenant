package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.overheating;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overheating.OverheatingHealthEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overheating.OverheatingWaterEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class OverheatingDebuff extends Ability {
  public OverheatingDebuff() {
    this.addEffect(new OverheatingWaterEffect());
    this.addEffect(new OverheatingHealthEffect());
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
    return Component.text(TextureModel.OVERHEAT_DEBUFF_SMALL.getChar() + " §cHyperthermie");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§cDu verbrauchst §cviermal§7 so"),
            Component.text("§cviel Wasser und verlierst langsam Leben.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Hyperthermie";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.OVERHEAT_DEBUFF;
  }
}

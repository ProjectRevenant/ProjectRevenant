package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.campfire;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor.ColdInsulationEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class CampfireBuff extends Ability {

  public CampfireBuff() {
    this.addEffect(new ColdInsulationEffect(7.5, "campfire"));
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
    return Component.text(TextureModel.CAMPFIRE_BUFF_SMALL.getChar() + " §aLagerfeuer");
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.CAMPFIRE_BUFF;
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du bist an einem Lagerfeuer."),
            Component.text("§7Dadurch erhälst §eKälteresistenz"),
            Component.text("§7und erwärmst dich ein wenig.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Lagerfeuer";
  }
}

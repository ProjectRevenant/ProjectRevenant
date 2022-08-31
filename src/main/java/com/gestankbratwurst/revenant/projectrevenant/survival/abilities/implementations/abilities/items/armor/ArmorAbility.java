package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor.ArmorEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor.ColdInsulationEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor.HeatInsulationEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ArmorAbility extends Ability {

  public ArmorAbility(double armor, double heatInsulation, double coldInsulation, String sufix) {
    this.addEffect(new ArmorEffect(armor, sufix));
    this.addEffect(new HeatInsulationEffect(heatInsulation, sufix));
    this.addEffect(new ColdInsulationEffect(coldInsulation, sufix));
  }

  public double getArmor(){
    return getEffect(ArmorEffect.class).getArmor();
  }

  public double getHeatInsulation(){
    return getEffect(HeatInsulationEffect.class).getAmount();
  }

  public double getColdInsulation(){
    return getEffect(ColdInsulationEffect.class).getAmount();
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
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text("§9Rüstung");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    List<Component> output = new ArrayList<>();
    output.add(Component.text(String.format("§7Hat §e%.1f §7Physische Rüstung", getArmor())));

    if(getHeatInsulation() > 0){
      output.add(Component.text(String.format("§7Hitzeschutz von §e%.1f§7°C", getHeatInsulation())));
    }
    if(getColdInsulation() > 0){
      output.add(Component.text(String.format("§7Kälteschutz von §e%.1f§7°C", getColdInsulation())));
    }

    return output;
  }

  @Override
  public String getPlainTextName() {
    return "Rüstung";
  }
}

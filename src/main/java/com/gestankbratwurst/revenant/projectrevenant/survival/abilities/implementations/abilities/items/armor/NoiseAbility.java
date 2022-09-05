package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor.NoiseEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NoiseAbility extends Ability {

  public NoiseAbility(){
    this(0.0, "NULL");
  }

  public NoiseAbility(double amount, String sufix){
    this.addEffect(new NoiseEffect(amount, sufix));
  }

  private double getAmount(){
    return getEffect(NoiseEffect.class).getAmount();
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
    return Component.text("§9Lautstärke");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    List<Component> output = new ArrayList<>();

    double amount = getAmount();

    if(amount != 0){
      if(amount < 0){
        output.add(Component.text(String.format("§7Reduziert deine Lautstärke um §e%.1f", (-amount))));
      } else{
        output.add(Component.text(String.format("§7Erhöht deine Lautstärke um §e%.1f", amount)));
      }
    }

    return output;
  }

  @Override
  public String getPlainTextName() {
    return "Lautstärke";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food.FoodEatenEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FoodEatenAbility extends Ability {
  private final double nutrition;
  private final double water;

  public FoodEatenAbility(double nutrition, double water){
    this.nutrition = nutrition;
    this.water = water;
    this.addEffect(new FoodEatenEffect(nutrition, water));
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
    return Component.text("§9Essbar");
  }

  @Override
  public List<Component> getInfos(Player viewer) {

    List<Component> output = new ArrayList<>();
    if(nutrition > 0){
      output.add(Component.text(String.format("§7Enthält §e%d§7kCal.", (int) nutrition)));
    }
    if(water > 0){
      output.add(Component.text(String.format("§7Enthält §e%.1f§7l Wasser.", water * 1000)));
    }

    return output;
  }

  @Override
  public String getPlainTextName() {
    return "Essbar";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }

}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food.FoodEatenEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodEatenAbility extends Ability {
  private final double nutrition;
  private final double water;
  private final double health;
  private final Duration healthDuration;

  public FoodEatenAbility(double nutrition, double water, double health, Duration healthDuration){
    this.nutrition = nutrition;
    this.water = water;
    this.health = health;
    this.healthDuration = healthDuration;
    this.addEffect(new FoodEatenEffect(nutrition, water, health, healthDuration));
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
      output.add(Component.text(String.format("§7Enthält §e%d§7ml Wasser.", (int) water)));
    }
    if(health > 0){
      output.add(Component.text(String.format("§7Heilt §e%.1f §7Leben über §e%.2f Minuten", water, (healthDuration.getSeconds() / 60d))));
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

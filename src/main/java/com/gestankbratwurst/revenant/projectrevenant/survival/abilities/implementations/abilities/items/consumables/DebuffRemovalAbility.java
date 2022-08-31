package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.reflections.ReflectionHelper;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.DebuffRemovalEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class DebuffRemovalAbility extends Ability {

  private final List<String> componentStrings;

  public DebuffRemovalAbility() {
    this(List.of());
  }

  public DebuffRemovalAbility(List<Class<? extends Ability>> remove) {
    this.componentStrings = new ArrayList<>();

    for (Class<? extends Ability> abilityClass : remove) {
      componentStrings.add(ReflectionHelper.constructInstance(abilityClass).getPlainTextName());
    }

    this.addEffect(new DebuffRemovalEffect(remove));
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
    return Component.text("§9Heilt");
  }


  @Override
  public List<Component> getInfos(Player viewer) {
    List<Component> output = new ArrayList<>();

    for (String effect : componentStrings) {
      output.add(Component.text("§e" + effect));
    }

    return output;
  }

  @Override
  public String getPlainTextName() {
    return "Debuff Entfernung";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

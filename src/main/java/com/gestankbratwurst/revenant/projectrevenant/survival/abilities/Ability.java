package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class Ability {

  private final Map<AbilityTrigger<?>, Map<String, AbilityEffect<?>>> triggerMap;
  @Getter
  private final String identifier;

  public Ability(String identifier) {
    triggerMap = new HashMap<>();
    this.identifier = identifier;
  }

  public <T> void addEffect(AbilityEffect<T> abilityEffect) {
    triggerMap.computeIfAbsent(abilityEffect.getTrigger(), key -> new HashMap<>()).put(abilityEffect.getIdentifier(), abilityEffect);
  }

  public <T> void removeEffect(AbilityEffect<T> abilityEffect) {
    triggerMap.computeIfAbsent(abilityEffect.getTrigger(), key -> new HashMap<>()).remove(abilityEffect.getIdentifier());
  }

  public void removeEffect(String identifier) {
    triggerMap.values().forEach(map -> map.remove(identifier));
  }

  public AbilityEffect<?> getEffect(String identifier) {
    return triggerMap.values()
            .stream()
            .flatMap(map -> map.values().stream())
            .filter(effect -> effect.getIdentifier().equals(identifier))
            .findAny()
            .orElse(null);
  }

  public <C, T> void reactOn(C caster, AbilityTrigger<T> trigger, T target) {
    Map<String, AbilityEffect<?>> effects = triggerMap.get(trigger);
    if (effects == null) {
      return;
    }
    for (AbilityEffect<?> effect : effects.values()) {
      ((AbilityEffect<T>) effect).cast(target);
    }
  }

  public abstract boolean shouldDisplayInTab();

  public abstract boolean shouldDisplayInActionbar();

  public abstract Component getInfoTitle(Player viewer);

  public abstract List<Component> getInfos(Player viewer);

  public abstract TextureModel getModel();

  @Override
  public int hashCode() {
    return identifier.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Ability other && identifier.equals(other.identifier);
  }

}

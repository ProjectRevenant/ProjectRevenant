package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class Ability {

  private final Map<AbilityTrigger<?>, Map<Class<? extends AbilityEffect<?>>, AbilityEffect<?>>> triggerMap;

  public Ability() {
    triggerMap = new HashMap<>();
  }

  public <T> void addEffect(AbilityEffect<T> abilityEffect) {
    triggerMap.computeIfAbsent(abilityEffect.getTrigger(), key -> new HashMap<>()).put((Class<? extends AbilityEffect<?>>) abilityEffect.getClass(), abilityEffect);
  }

  public <T> void removeEffect(AbilityEffect<T> abilityEffect) {
    triggerMap.computeIfAbsent(abilityEffect.getTrigger(), key -> new HashMap<>()).remove(abilityEffect.getClass());
  }

  public <K, T extends AbilityEffect<K>> void removeEffect(Class<T> identifier) {
    triggerMap.values().forEach(map -> map.remove(identifier));
  }

  public <K, T extends AbilityEffect<K>> T getEffect(Class<T> identifier) {
    for(Map<Class<? extends AbilityEffect<?>>, AbilityEffect<?>> map : triggerMap.values()) {
      if(map.containsKey(identifier)) {
        return (T) map.get(identifier);
      }
    }
    return null;
  }

  public <C, T> void reactOn(C caster, AbilityTrigger<T> trigger, T target) {
    Map<Class<? extends AbilityEffect<?>>, AbilityEffect<?>> effects = triggerMap.get(trigger);
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

  public abstract String getPlainTextName();

  public abstract TextureModel getModel();

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Ability other && this.getClass().equals(other.getClass());
  }

}

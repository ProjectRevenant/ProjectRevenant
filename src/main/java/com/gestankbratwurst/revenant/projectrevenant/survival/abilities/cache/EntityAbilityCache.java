package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.tablist.implementation.AbstractTabList;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluationRegistry;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EntityAbilityCache {

  private static final Map<UUID, Map<Class<? extends Ability>, Ability>> abilityCache = new HashMap<>();

  public static void uncache(UUID entityId) {
    abilityCache.remove(entityId);
  }

  public static void cache(UUID entityId) {
    abilityCache.put(entityId, new HashMap<>());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Ability> T getAbility(UUID entityId, Class<T> identifier) {
    return (T) Optional.ofNullable(abilityCache.get(entityId)).map(abilities -> abilities.get(identifier)).orElse(null);
  }

  public static Collection<Ability> getAbilities(UUID entityId) {
    return List.copyOf(abilityCache.getOrDefault(entityId, Collections.emptyMap()).values());
  }

  public static void updateAbilities(UUID entityId, List<Ability> abilityList) {
    Map<Class<? extends Ability>, Ability> map = abilityCache.computeIfAbsent(entityId, key -> new HashMap<>());
    map.clear();
    abilityList.forEach(ability -> map.put(ability.getClass(), ability));
  }

  public static <T extends Entity> void autoUpdate(T entity, Class<T> type) {
    List<Ability> abilityList = AbilityEvaluationRegistry.getTyped(type).orElseThrow().evaluate(entity).stream().filter(ability -> {
      if(ability instanceof TimedAbility timedAbility) {
        if(!timedAbility.hasStarted()) {
          return true;
        }
        return !timedAbility.isDone();
      }
      return true;
    }).toList();
    EntityAbilityCache.updateAbilities(entity.getUniqueId(), abilityList);
    if(entity instanceof LivingEntity livingEntity) {
      ProjectRevenant.getBodyManager().getBody(livingEntity).recalculateAttributes();
    }
    if(entity instanceof Player player) {
      AbstractTabList tabList = MMCore.getTabListManager().getView(player).getTablist();
      if(tabList instanceof RevenantUserTablist userTablist) {
        userTablist.updateEffects();
        userTablist.updateBody();
      }
    }
  }
}

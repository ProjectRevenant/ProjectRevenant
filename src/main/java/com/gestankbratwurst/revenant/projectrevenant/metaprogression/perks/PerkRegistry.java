package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PerkRegistry {

  private static final Map<Class<? extends PerkAbility>, Perk<?>> perkMap = new HashMap<>();

  public static <T extends PerkAbility> T createPerkInstance(Class<T> perkClass) {
    return (T) perkMap.get(perkClass).getPerkAbilitySupplier().get();
  }

  public static <T extends PerkAbility> void registerPerk(Class<T> perkClass, Perk<T> perk) {
    perkMap.put(perkClass, perk);
  }

}

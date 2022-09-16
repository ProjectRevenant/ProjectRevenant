package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks.SaveHungerPerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks.SaveWaterPerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks.StrongSkinPerkAbility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PerkRegistry {

  private static final Map<Class<? extends PerkAbility>, Perk<?>> perkMap = new HashMap<>();

  public static <T extends PerkAbility> T createPerkInstance(Class<T> perkClass) {
    return (T) perkMap.get(perkClass).getPerkAbilitySupplier().get();
  }

  public static <T extends PerkAbility> void registerPerk(Perk<T> perk) {
    perkMap.put(perk.getAbilityClass(), perk);
  }

  public static List<Class<? extends PerkAbility>> getAllRegisteredClasses() {
    return new ArrayList<>(perkMap.keySet());
  }

  public static <T extends PerkAbility> Perk<T> getPerk(Class<T> perkClass) {
    return (Perk<T>) perkMap.get(perkClass);
  }

  static {
    registerPerk(new Perk<>(StrongSkinPerkAbility.class, StrongSkinPerkAbility::new, PerkType.SURVIVAL, 1));
    registerPerk(new Perk<>(SaveHungerPerkAbility.class, SaveHungerPerkAbility::new, PerkType.SURVIVAL, 1));
    registerPerk(new Perk<>(SaveWaterPerkAbility.class, SaveWaterPerkAbility::new, PerkType.SURVIVAL, 1));
  }

}

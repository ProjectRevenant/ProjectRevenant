package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks.SaveHungerPerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks.SaveWaterPerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.perks.StrongSkinPerkAbility;

import java.util.HashMap;
import java.util.Map;

public class PerkLayout {

  private static final Map<PerkType, int[][]> tilemap = new HashMap<>();
  private static final Map<PerkType, Map<Integer, Class<? extends PerkAbility>>> perkIds = new HashMap<>();

  static {
    setupSurvival();
    setupCombat();
    setupUtility();

    setupPerkIds();

    for (Class<? extends PerkAbility> perkAbilityClass : PerkRegistry.getAllRegisteredClasses()) {
      Perk<?> perk = PerkRegistry.getPerk(perkAbilityClass);
      if (!perkIds.get(perk.getPerkType()).containsValue(perkAbilityClass)) {
        throw new IllegalStateException("No ui layout id set for " + perkAbilityClass + ".");
      }
    }

    for (PerkType perkType : PerkType.values()) {
      if (!tilemap.containsKey(perkType)) {
        throw new IllegalStateException("No layout for " + perkType + " setup.");
      }
    }
  }

  public static int getMaxYOffset(PerkType perkType) {
    return tilemap.get(perkType).length - 6;
  }

  public static int getIdAt(PerkType perkType, int x, int y) {
    int[][] tiles = tilemap.get(perkType);
    if(tiles.length < y) {
      return -1;
    }
    int[] row = tiles[y];
    if(row.length < x) {
      return -1;
    }
    return row[x];
  }

  public static Perk<?> getPerkOfId(PerkType type, int id) {
    if(id < 0) {
      return null;
    }
    return PerkRegistry.getPerk(perkIds.get(type).get(id));
  }

  private static <T extends PerkAbility> void addPerk(Class<T> perkClass, int id) {
    perkIds.computeIfAbsent(PerkRegistry.getPerk(perkClass).getPerkType(), key -> new HashMap<>()).put(id, perkClass);
  }

  private static void setupPerkIds() {
    addPerk(StrongSkinPerkAbility.class, 1);
    addPerk(SaveWaterPerkAbility.class, 2);
    addPerk(SaveHungerPerkAbility.class, 3);
  }

  private static void setupSurvival() {
    tilemap.put(PerkType.SURVIVAL, new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 3, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    });
  }

  private static void setupCombat() {
    tilemap.put(PerkType.COMBAT, new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    });
  }

  private static void setupUtility() {
    tilemap.put(PerkType.UTILITY, new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    });
  }

}

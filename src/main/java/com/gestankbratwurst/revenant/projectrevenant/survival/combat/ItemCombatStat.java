package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Projectile;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemCombatStat {

  private static final NamespacedKey projectileDamageKey = NamespaceFactory.provide("projectile-damage");

  public static double fetchProjectileDamage(Projectile projectile) {
    PersistentDataContainer container = projectile.getPersistentDataContainer();
    double speed = projectile.getVelocity().length();
    double damage = container.getOrDefault(projectileDamageKey, PersistentDataType.DOUBLE, 10.0);
    return speed * damage;
  }

  public static void applyProjectileDamage(Projectile projectile, double damage) {
    PersistentDataContainer container = projectile.getPersistentDataContainer();
    container.set(projectileDamageKey, PersistentDataType.DOUBLE, damage);
  }

}

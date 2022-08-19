package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;

public class CombatEvaluator {

  private static final Map<EntityDamageEvent.DamageCause, Double> environmentalPercentageDamages = Map.copyOf(
          new HashMap<>() {{
            put(EntityDamageEvent.DamageCause.ENTITY_ATTACK, 0.0);
            put(EntityDamageEvent.DamageCause.FALLING_BLOCK, 0.1);
            put(EntityDamageEvent.DamageCause.FALL, 0.05);
            put(EntityDamageEvent.DamageCause.FIRE, 0.05);
            put(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, 0.0);
            put(EntityDamageEvent.DamageCause.FIRE_TICK, 0.025);
            put(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, 0.05);
            put(EntityDamageEvent.DamageCause.CONTACT, 0.025);
            put(EntityDamageEvent.DamageCause.CRAMMING, 0.025);
            put(EntityDamageEvent.DamageCause.CUSTOM, 0.025);
            put(EntityDamageEvent.DamageCause.DRAGON_BREATH, 0.025);
            put(EntityDamageEvent.DamageCause.DROWNING, 0.05);
            put(EntityDamageEvent.DamageCause.DRYOUT, 0.025);
            put(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, 0.025);
            put(EntityDamageEvent.DamageCause.FLY_INTO_WALL, 0.1);
            put(EntityDamageEvent.DamageCause.FREEZE, 0.01);
            put(EntityDamageEvent.DamageCause.HOT_FLOOR, 0.02);
            put(EntityDamageEvent.DamageCause.LAVA, 0.05);
            put(EntityDamageEvent.DamageCause.LIGHTNING, 0.05);
            put(EntityDamageEvent.DamageCause.MAGIC, 0.05);
            put(EntityDamageEvent.DamageCause.MELTING, 0.05);
            put(EntityDamageEvent.DamageCause.POISON, 0.025);
            put(EntityDamageEvent.DamageCause.PROJECTILE, 0.0);
            put(EntityDamageEvent.DamageCause.SONIC_BOOM, 0.1);
            put(EntityDamageEvent.DamageCause.STARVATION, 0.01);
            put(EntityDamageEvent.DamageCause.SUFFOCATION, 0.01);
            put(EntityDamageEvent.DamageCause.SUICIDE, 1.0);
            put(EntityDamageEvent.DamageCause.THORNS, 0.02);
            put(EntityDamageEvent.DamageCause.VOID, 0.05);
            put(EntityDamageEvent.DamageCause.WITHER, 0.025);
          }}
  );

  public static double evaluateAttack(Entity attacker, LivingEntity defender) {
    double damage = 0;
    if(attacker instanceof Projectile projectile) {
      damage = ItemCombatStat.fetchProjectileDamage(projectile);
    } else if(attacker instanceof LivingEntity livingAttacker) {
      Body attackerBody = ProjectRevenant.getBodyManager().getBody(livingAttacker);
      damage = attackerBody.getAttribute(BodyAttribute.MELEE_DAMAGE).getCurrentValueModified();
    } else {
      throw new RuntimeException("Unhandled damage by " + attacker.getType());
    }
    double defence = ProjectRevenant.getBodyManager().getBody(defender).getAttribute(BodyAttribute.PHYSICAL_ARMOR).getCurrentValueModified();
    double defenceScalar = 0.9 * Math.tanh(-defence * 0.0075) + 1;
    return damage * defenceScalar;
  }

  public static double evaluateDamage(LivingEntity defender, EntityDamageEvent.DamageCause damageCause, double initialDamage) {
    double percentage = environmentalPercentageDamages.getOrDefault(damageCause, 0.0) * initialDamage;
    Body body = ProjectRevenant.getBodyManager().getBody(defender);
    BodyAttribute healthAttr = body.getAttribute(BodyAttribute.HEALTH);
    return percentage * healthAttr.getMaxValueModified();
  }

}

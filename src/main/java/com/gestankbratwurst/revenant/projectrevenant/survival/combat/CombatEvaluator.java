package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CombatEvaluator {

  private final static double looseStack = 0.65;
  private final static double loosePartOfStack = 0.85;
  private final static double minLost = 0.5;
  private final static double maxLost = 0.8;

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

  private static final double maxPercentageDamageReduction = 0.9;
  private static final double curveAmplifier = 0.0075;

  public static double evaluateAttack(Entity attacker, LivingEntity defender, boolean vanillaCrit, double modifier) {
    double damage;
    if (attacker instanceof Projectile projectile) {
      damage = ItemCombatStat.fetchProjectileDamage(projectile);
    } else if (attacker instanceof LivingEntity livingAttacker) {
      float cooldownMod = attacker instanceof Player player ? PlayerSwingActionEvaluator.getCd(player) : 1.0F;
      cooldownMod = cooldownMod * cooldownMod * cooldownMod * cooldownMod;
      Body attackerBody = ProjectRevenant.getBodyManager().getBody(livingAttacker);
      damage = attackerBody.getAttribute(BodyAttribute.MELEE_DAMAGE).getCurrentValueModified() * cooldownMod;
    } else {
      throw new RuntimeException("Unhandled damage by " + attacker.getType());
    }

    damage *= modifier;
    double defence = ProjectRevenant.getBodyManager().getBody(defender).getAttribute(BodyAttribute.PHYSICAL_ARMOR).getCurrentValueModified();

    double defenceScalar = maxPercentageDamageReduction * Math.tanh(-defence * curveAmplifier) + 1;
    return damage * defenceScalar * (vanillaCrit ? 1.15 : 1.0);
  }

  public static double evaluateEnvironmentalDamage(LivingEntity defender, EntityDamageEvent.DamageCause damageCause, double initialDamage) {
    double percentage = environmentalPercentageDamages.getOrDefault(damageCause, 0.0) * initialDamage;
    Body body = ProjectRevenant.getBodyManager().getBody(defender);
    BodyAttribute healthAttr = body.getAttribute(BodyAttribute.HEALTH);
    return percentage * healthAttr.getMaxValueModified();
  }

  public static void managePlayerItemDrops(List<ItemStack> inventory, Block block) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    World world = block.getWorld();

    for (ItemStack item : inventory) {
      if (item == null) {
        continue;
      }

      if (random.nextDouble() <= looseStack) {
        item.setAmount(0);
      } else if (item.getAmount() > 1 && random.nextDouble() <= loosePartOfStack) {
        int newAmount = (int) (item.getAmount() * random.nextDouble(minLost, maxLost) + 0.5);
        item.setAmount(newAmount);
      }
    }

    for (ItemStack item : inventory) {
      if(item == null){
        continue;
      }
      world.dropItem(block.getLocation().add(0.5, 0.5, 0.5), item);
    }
  }

}

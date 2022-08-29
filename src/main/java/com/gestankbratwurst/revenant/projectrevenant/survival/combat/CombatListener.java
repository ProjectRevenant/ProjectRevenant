package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

@RequiredArgsConstructor
public class CombatListener implements Listener {

  private final BodyManager bodyManager;

  @EventHandler(priority = EventPriority.LOW)
  public void onDamage(EntityDamageEvent event) {
    if (event instanceof EntityDamageByEntityEvent combatEvent) {
      onCombat(combatEvent);
      return;
    }
    if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
      return;
    }
    double damage = event.getDamage();
    EntityDamageEvent.DamageCause cause = event.getCause();
    event.setDamage(CombatEvaluator.evaluateEnvironmentalDamage(livingEntity, cause, damage));
  }

  public void onCombat(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
      return;
    }
    event.setDamage(CombatEvaluator.evaluateAttack(event.getDamager(), livingEntity, event.isCritical()));
  }

  @EventHandler
  public void onLaunch(ProjectileLaunchEvent event) {
    Projectile projectile = event.getEntity();
    if (projectile.getShooter() instanceof LivingEntity livingShooter) {
      double damage = bodyManager.getBody(livingShooter).getAttribute(BodyAttribute.RANGED_DAMAGE).getCurrentValueModified();
      ItemCombatStat.applyProjectileDamage(projectile, damage);
    }
  }

}

package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.protocol.holograms.MovingHologram;
import com.gestankbratwurst.core.mmcore.protocol.holograms.impl.Hologram;
import com.gestankbratwurst.core.mmcore.protocol.holograms.impl.HologramManager;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class CombatListener implements Listener {

  private final BodyManager bodyManager;

  @EventHandler(priority = EventPriority.LOW)
  public void onDamage(EntityDamageEvent event) {
    if (event instanceof EntityDamageByEntityEvent combatEvent) {
      onCombat(combatEvent);
    } else {
      if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
        return;
      }
      double damage = event.getDamage();
      EntityDamageEvent.DamageCause cause = event.getCause();
      event.setDamage(CombatEvaluator.evaluateEnvironmentalDamage(livingEntity, cause, damage));
    }

    Location location = event.getEntity().getLocation().add(0.5, 0.5, 0.5);
    HologramManager hologramManager = MMCore.getHologramManager();
    MovingHologram moving = hologramManager.createMovingHologram(location, new Vector(0, 0.025, 0), 30);
    moving.getHologram().appendTextLine(TextureModel.WAR_ICON.getChar() + " §c-%.1f".formatted(event.getDamage()));
  }

  public void onCombat(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
      return;
    }
    double critChance = bodyManager.getBody(livingEntity).getAttribute(BodyAttribute.CRITICAL_STRIKE_CHANCE).getCurrentValueModified();
    double modifier = 1.0;
    if(ThreadLocalRandom.current().nextDouble() <= critChance) {
      double critDamage = bodyManager.getBody(livingEntity).getAttribute(BodyAttribute.CRITICAL_STRIKE_DAMAGE).getCurrentValueModified();
      modifier += critDamage / 100.0;
    }

    event.setDamage(CombatEvaluator.evaluateAttack(event.getDamager(), livingEntity, event.isCritical(), modifier));
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

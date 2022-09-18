package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.protocol.holograms.MovingHologram;
import com.gestankbratwurst.core.mmcore.protocol.holograms.impl.HologramManager;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class CombatListener implements Listener {

  private final BodyManager bodyManager;

  @EventHandler(priority = EventPriority.LOW)
  public void onDamage(EntityDamageEvent event) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(event.getEntity().getUniqueId());
    if(revenantPlayer != null) {
      TaskManager.getInstance().runBukkitSync(() -> MMCore.getActionBarManager().update((Player) event.getEntity()));
      if(revenantPlayer.isInSpawnPod()) {
        event.setCancelled(true);
        return;
      }
    }
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

    if(event.getEntity() instanceof Player defendingPlayer){
      RevenantPlayer.of(defendingPlayer).setDamageLog();
    }

    if(event.getDamager() instanceof Player attackingPlayer){
      RevenantPlayer.of(attackingPlayer).setDamageLog();
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

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event){
    Player player = event.getPlayer();
    List<ItemStack> droppedItems = event.getDrops();
    event.getDrops().clear();

    if(player.getGameMode() == GameMode.SPECTATOR || player.getGameMode() == GameMode.CREATIVE){
      return;
    }

    CombatEvaluator.managePlayerItemDrops(droppedItems, player.getLocation());
  }

}

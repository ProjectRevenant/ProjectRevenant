package com.gestankbratwurst.revenant.projectrevenant.mobs.implementations;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class SmartNoiseTargetGoal<Zombie> extends TargetGoal {

  private final int randomInterval;
  private LivingEntity target;
  private final TargetingConditions targetConditions;

  public SmartNoiseTargetGoal(Mob mob, boolean checkVisibility, double minAttractionNoise) {
    super(mob, false, false);
    this.randomInterval = reducedTickDelay(10);
    this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(livingEntity -> {

      if (!(livingEntity instanceof Player player)) {
        return false;
      }

      double noise = RevenantPlayer.of(player.getUUID()).getNoiseLevelAt(mob.getBukkitEntity().getLocation());
      System.out.println("[DEBUG] Target player " + player.getName() + " has noise level: " + noise);

      if (noise >= minAttractionNoise) {
        return true;
      }

      return mob.getSensing().hasLineOfSight(player);

    }).ignoreLineOfSight();
  }


  @Override
  public boolean canUse() {
    if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
      return false;
    } else {
      this.findTarget();
      return this.target != null;
    }

  }

  private void findTarget() {
    this.target = this.mob.level.getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    System.out.println("[DEBUG] Set current target to: " + target);
  }

  @Override
  public void start() {
    this.mob.setTarget(this.target, this.target instanceof ServerPlayer ? org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_PLAYER : org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true); // CraftBukkit - reason
    super.start();
  }

  @Override
  public boolean isInterruptable() {
    return true;
  }

  @Override
  public boolean requiresUpdateEveryTick() {
    return false;
  }


}

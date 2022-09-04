package com.gestankbratwurst.revenant.projectrevenant.mobs.implementations;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.mobs.RevenantMob;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.entity.LivingEntityBody;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class RevenantZombie extends Zombie implements RevenantMob<RevenantZombie> {

  private static final double minAttractionNoise = 20;
  private static final double attackSpeed = 1.0;
  private static final double meleeDamage = 10;
  private static final double baseHealth = 50;
  private static final double meleeKnockback = 1.0;
  private static final double speed = 1.5;

  public RevenantZombie(EntityType<? extends Zombie> type, Level world) {
    super(EntityType.ZOMBIE, world);
  }
  @Override
  public Body createDefaultBody() {
    return LivingEntityBody.builder()
            .base(BodyAttribute.ATTACK_SPEED, 0.5, 3, attackSpeed)
            .base(BodyAttribute.MELEE_DAMAGE, 0, 100, meleeDamage)
            .base(BodyAttribute.HEALTH, 0, 200, baseHealth)
            .base(BodyAttribute.MELEE_KNOCKBACK, 0, 10, meleeKnockback)
            .base(BodyAttribute.SPEED, 0, 20, speed)
            .create();
  }
  @Override
  protected void registerGoals() {
    Predicate<LivingEntity> noiseCondition = target -> {
      if(!(target instanceof Player player)) {
        return false;
      }
      return RevenantPlayer.of(player.getUUID()).getNoiseLevelAt(this.getBukkitEntity().getLocation()) > minAttractionNoise;
    };
    this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    this.goalSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false, noiseCondition));
    this.goalSelector.addGoal(4, new ZombieAttackGoal(this, getActiveBody().getAttribute(BodyAttribute.SPEED).getCurrentValueModified(), true));
  }

  @Override
  public List<Ability> getActiveAbilities() {
    return List.of();
  }

  @Override
  public UUID getMobId() {
    return this.uuid;
  }

  @Override
  public RevenantZombie getSelf() {
    return this;
  }

  @Override
  public void postSpawnSetup() {

  }

}

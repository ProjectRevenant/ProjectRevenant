package com.gestankbratwurst.revenant.projectrevenant.mobs.implementations;

import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomEntityType;
import com.gestankbratwurst.revenant.projectrevenant.mobs.RevenantMob;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.entity.LivingEntityBody;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class RevenantZombie extends Zombie implements RevenantMob<RevenantZombie> {

  private static final double minAttractionNoise = 20;
  private static final double attackSpeed = 1.0;
  private static final double meleeDamage = 10;
  private static final double baseHealth = 50;
  private static final double meleeKnockback = 1.0;
  private static final double speed = 20;
  private static final double chargeSpeedScalar = 1.1;

  public RevenantZombie(EntityType<? extends Zombie> type, Level world) {
    super(EntityType.ZOMBIE, world);
    this.setShouldBurnInDay(false);
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
    this.goalSelector.addGoal(2, new SmartNoiseTargetGoal<>(this, false, minAttractionNoise));
    this.goalSelector.addGoal(3, new ZombieAttackGoal(this, chargeSpeedScalar, false));
  }

  @Override
  public boolean save(CompoundTag nbt) {
    boolean saved = super.save(nbt);
    if (saved) {
      nbt.putString("id", CustomEntityType.REVENANT_ZOMBIE.id);
    }
    return saved;
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

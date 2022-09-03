package com.gestankbratwurst.revenant.projectrevenant.mobs.implementations;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomEntityType;
import com.gestankbratwurst.revenant.projectrevenant.mobs.RevenantMob;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.entity.LivingEntityBody;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CustomChicken extends Chicken implements RevenantMob<CustomChicken> {

  private final ActiveModel model;

  public CustomChicken(EntityType<? extends Chicken> type, Level world) {
    super(EntityType.CHICKEN, world);
    AttributeMap attributes = this.getAttributes();
    attributes.registerAttribute(Attributes.ATTACK_DAMAGE);
    this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1);
    this.model = ModelEngineAPI.createActiveModel("stoneball");
  }

  @Override
  protected void registerGoals() {
    super.registerGoals();
    this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2, true));
  }

  @Override
  public void swing(InteractionHand hand) {
    int type = ThreadLocalRandom.current().nextInt(3);
    model.getAnimationHandler().playAnimation("attack_" + type, 1, 1, 1);
    super.swing(hand);
  }

  @Override
  public boolean save(CompoundTag nbt) {
    boolean saved = super.save(nbt);
    if (saved) {
      nbt.putString("id", CustomEntityType.CUSTOM_CHICKEN.id);
    }
    return saved;
  }

  @Override
  public void load(CompoundTag nbt) {
    super.load(nbt);
    this.postSpawnSetup();
  }

  @Override
  public Body createDefaultBody() {
    return LivingEntityBody.builder()
            .base(BodyAttribute.ATTACK_SPEED, 0.5, 2, 1.0)
            .base(BodyAttribute.MELEE_DAMAGE, 0, 20, 5)
            .base(BodyAttribute.HEALTH, 0, 20, 10)
            .base(BodyAttribute.MELEE_KNOCKBACK, 0, 10, 10)
            .base(BodyAttribute.SPEED, 0, 40, 20)
            .create();
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
  public CustomChicken getSelf() {
    return this;
  }

  @Override
  public void postSpawnSetup() {
    ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(this.getBukkitEntity());
    modeledEntity.addModel(model, true);
    modeledEntity.setBaseEntityVisible(false);
    this.setSilent(true);
  }
}

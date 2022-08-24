package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.data.json.DeserializationPostProcessable;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.campfire.CampfireBuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.dry.DryBuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wet.WetDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.items.ItemAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.weight.ItemWeight;
import com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment.WorldEnvironmentFetcher;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class Body implements DeserializationPostProcessable {

  protected final Map<String, BodyAttribute> attributeMap;
  @Getter
  @Setter
  private transient UUID entityId;
  @Getter
  private transient int ticksAlive;

  public Body() {
    this.attributeMap = new HashMap<>();
    for (String attributeId : BodyAttribute.getValues()) {
      attributeMap.put(attributeId, new BodyAttribute(attributeId, 1.0));
    }
  }

  public BodyAttribute getAttribute(String identifier) {
    return attributeMap.get(identifier);
  }

  public void recalculateAttributes() {
    LivingEntity entity = (LivingEntity) Bukkit.getEntity(entityId);
    if (entity == null) {
      return;
    }
    recalculateWeight();
    for (String attributeId : BodyAttribute.getValues()) {
      getAttribute(attributeId).clearModifiers();
    }
    EntityEquipment equipment = entity.getEquipment();
    if (equipment != null) {
      for (EquipmentSlot slot : EquipmentSlot.values()) {
        ItemStack itemStack = equipment.getItem(slot);
        for (BodyAttributeModifier modifier : ItemAttribute.getAttributes(itemStack)) {
          this.getAttribute(modifier.getBodyAttribute()).addModifier(modifier);
        }
      }
    }
    for (Ability ability : EntityAbilityCache.getAbilities(entityId)) {
      ability.reactOn(entity, AbilityTrigger.PASSIVE_ATTRIBUTE, this);
    }
    BodyAttribute speedAttribute = getAttribute(BodyAttribute.SPEED);
    double mcSpeed = speedAttribute.getCurrentValueModified() / (22.25 * 5);
    Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(mcSpeed);
  }

  public void tick() {
    ticksAlive++;
    if (ticksAlive % 20 == 0) {
      tickSeconds();
    }
    if (attributeMap.get(BodyAttribute.HEALTH).getCurrentValue() <= 0.0001) {
      Optional.ofNullable(Bukkit.getEntity(entityId)).map(LivingEntity.class::cast).ifPresent(entity -> {
        entity.setHealth(0);
        entity.damage(0);
      });
      return;
    }
    attributeMap.get(BodyAttribute.HEALTH).applyToCurrentValue(current -> current + attributeMap.get(BodyAttribute.HEALTH_SHIFT).getCurrentValueModified());
    attributeMap.get(BodyAttribute.WATER).applyToCurrentValue(current -> current + attributeMap.get(BodyAttribute.WATER_SHIFT).getCurrentValueModified());
    attributeMap.get(BodyAttribute.NUTRITION).applyToCurrentValue(current -> current + attributeMap.get(BodyAttribute.NUTRITION_SHIFT).getCurrentValueModified());
    attributeMap.get(BodyAttribute.WEIGHT).applyToCurrentValueUnsafe(current -> current + attributeMap.get(BodyAttribute.WEIGHT_SHIFT).getCurrentValue());
    if (attributeMap.get(BodyAttribute.HEALTH).getCurrentValue() <= 0.0001) {
      Optional.ofNullable(Bukkit.getEntity(entityId)).map(LivingEntity.class::cast).ifPresent(entity -> {
        entity.setHealth(0);
        entity.damage(0);
      });
    }
  }

  protected void tickSeconds() {
    Entity entity = Bukkit.getEntity(entityId);
    if (!(entity instanceof LivingEntity livingEntity)) {
      return;
    }
    // TODO: If performance is good then enable this for all LivingEntities
    this.checkIfWet(livingEntity);
    this.checkIfDry(livingEntity);
  }

  private void checkIfWet(LivingEntity entity) {
    if (!(entity instanceof Player player)) {
      return;
    }
    Block block = entity.getLocation().getBlock();
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    if (block.getType() == Material.WATER) {
      Ability wetAbility = revenantPlayer.getAbility(RevenantAbility.WET_DEBUFF);
      if (wetAbility == null) {
        revenantPlayer.addAbility(new WetDebuff());
        EntityAbilityCache.autoUpdate(player, Player.class);
      } else {
        ((WetDebuff) wetAbility).setLitres(2.0);
      }
      return;
    }
    boolean raining = entity.getWorld().hasStorm();
    if (!raining) {
      return;
    }
    double roofY = entity.getWorld().getHighestBlockAt(entity.getEyeLocation()).getY();
    if (roofY > entity.getEyeLocation().getY()) {
      return;
    }
    Ability wetAbility = revenantPlayer.getAbility(RevenantAbility.WET_DEBUFF);
    if (wetAbility == null) {
      WetDebuff debuff = new WetDebuff();
      debuff.setLitres(0.05);
      revenantPlayer.addAbility(debuff);
      EntityAbilityCache.autoUpdate(player, Player.class);
    } else {
      WetDebuff debuff = ((WetDebuff) wetAbility);
      debuff.setLitres(Math.min(debuff.getLitres() + 0.05, 2.0));
    }
  }

  private void checkIfDry(LivingEntity entity) {
    if (!(entity instanceof Player player)) {
      return;
    }
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    boolean dry = WorldEnvironmentFetcher.isDry(entity.getLocation(), false);
    boolean nearHeatSource = WorldEnvironmentFetcher.isNearHeatSource(entity.getLocation());
    if (dry || nearHeatSource) {
      Ability dryAbility = revenantPlayer.getAbility(RevenantAbility.DRY_BUFF);
      if (dryAbility == null) {
        DryBuff buff = new DryBuff();
        buff.setDurationFromNow(Duration.ofSeconds(10));
        revenantPlayer.addAbility(buff);
        EntityAbilityCache.autoUpdate(player, Player.class);
      } else {
        ((DryBuff) dryAbility).setDurationFromNow(Duration.ofSeconds(10));
      }
    }
    if (nearHeatSource) {
      if (!revenantPlayer.hasAbility(RevenantAbility.CAMPFIRE_BUFF)) {
        revenantPlayer.addAbility(new CampfireBuff());
      }
    } else if (revenantPlayer.hasAbility(RevenantAbility.CAMPFIRE_BUFF)) {
      revenantPlayer.removeAbility(RevenantAbility.CAMPFIRE_BUFF);
    }
  }

  protected void recalculateWeight() {
    LivingEntity entity = (LivingEntity) Bukkit.getEntity(entityId);
    if (entity == null) {
      new RuntimeException("Calculated weight for offline entity.").printStackTrace();
      return;
    }
    double weight = ItemWeight.of(entity);
    BodyAttribute weightAttr = getAttribute(BodyAttribute.WEIGHT);
    weightAttr.setCurrentValueUnsafe(weight);
    double max = weightAttr.getMaxValueModified();
    if (weight >= max) {
      if (entity instanceof Player player) {
        Msg.sendWarning(player, "Du bist überladen. {}", "%.1fkg/%.1fkg".formatted(weightAttr.getCurrentValue(), weightAttr.getMaxValueModified()));
      }
    }
    if (entity instanceof Player player) {
      MMCore.getActionBarManager().getBoard(player).update();
    }
  }

  @Override
  public void gsonPostProcess() {
    for (String attributeId : BodyAttribute.getValues()) {
      if (!attributeMap.containsKey(attributeId)) {
        attributeMap.put(attributeId, new BodyAttribute(attributeId, 1.0));
      }
    }
  }
}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluationRegistry;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluator;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LivingEntityAbilityEvaluator extends AbilityEvaluator<LivingEntity> {
  public LivingEntityAbilityEvaluator() {
    super(LivingEntity.class);
  }

  @Override
  public List<Ability> evaluate(LivingEntity target) {
    List<Ability> abilities = new ArrayList<>();
    AbilityEvaluator<ItemStack> itemEvaluator = AbilityEvaluationRegistry.getTyped(ItemStack.class).orElseThrow();
    EntityEquipment equipment = target.getEquipment();
    if (equipment == null) {
      return Collections.emptyList();
    }
    for (EquipmentSlot slot : EquipmentSlot.values()) {
      abilities.addAll(itemEvaluator.evaluate(equipment.getItem(slot)));
    }
    return abilities;
  }
}

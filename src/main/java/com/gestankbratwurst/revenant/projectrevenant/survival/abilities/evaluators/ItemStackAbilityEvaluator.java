package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluationRegistry;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Collections;
import java.util.List;

public class ItemStackAbilityEvaluator extends AbilityEvaluator<ItemStack> {
  public ItemStackAbilityEvaluator() {
    super(ItemStack.class);
  }

  @Override
  public List<Ability> evaluate(ItemStack target) {
    if(target == null) {
      return Collections.emptyList();
    }
    ItemMeta meta = target.getItemMeta();
    if(meta == null) {
      return Collections.emptyList();
    }
    PersistentDataContainer container = meta.getPersistentDataContainer();
    return AbilityEvaluationRegistry.getTyped(PersistentDataContainer.class).orElseThrow().evaluate(container);
  }
}

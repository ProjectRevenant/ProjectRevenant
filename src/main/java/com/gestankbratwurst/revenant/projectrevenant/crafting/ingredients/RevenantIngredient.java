package com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients;

import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.inventory.ItemStack;

public class RevenantIngredient extends SimpleIngredient {

  public RevenantIngredient(ItemStack itemStack) {
    super(itemStack);
  }

  public RevenantIngredient() {
    this(null);
  }

  @Override
  public boolean test(ItemStack itemStack) {
    return RevenantItem.sameInternalName(getItemStack(), itemStack);
  }
}

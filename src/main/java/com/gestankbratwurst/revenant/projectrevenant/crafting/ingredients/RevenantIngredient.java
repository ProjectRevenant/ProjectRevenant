package com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients;

import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.inventory.ItemStack;

public class RevenantIngredient extends SimpleIngredient {

  public RevenantIngredient(ItemStack itemStack) {
    this(itemStack, true);
  }

  public RevenantIngredient(ItemStack itemStack, boolean consumed) {
    super(itemStack, consumed);
  }

  public RevenantIngredient() {
    this(null, true);
  }

  @Override
  public boolean test(ItemStack itemStack) {
    return RevenantItem.sameInternalName(getItemStack(), itemStack);
  }
}

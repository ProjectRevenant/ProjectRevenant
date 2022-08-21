package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import org.bukkit.inventory.ItemStack;

public class SimpleItemLoot extends ItemLoot{

  public SimpleItemLoot(ItemStack item){
    super((player) -> item.clone());
  }

}

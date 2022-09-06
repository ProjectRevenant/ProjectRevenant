package com.gestankbratwurst.revenant.projectrevenant.crafting.implementation;

import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.crafting.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.ItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DummyRecipe implements RevenantRecipe {

  private static final UUID dummyId = UUID.fromString("87c774e7-b726-48a3-9d5f-2d0e0882a51f");

  @Override
  public UUID getId() {
    return dummyId;
  }

  @Override
  public Loot getResult() {
    return new SimpleItemLoot(RevenantItem.testItem());
  }

  @Override
  public ItemStack infoIconFor(Player player) {
    return RevenantItem.testItem();
  }

  @Override
  public void onCraft(Player player) {
    Msg.sendWarning(player, "Dummy recipe for debug purposes.");
  }

  @Override
  public boolean canCraft(Player player) {
    return true;
  }
}

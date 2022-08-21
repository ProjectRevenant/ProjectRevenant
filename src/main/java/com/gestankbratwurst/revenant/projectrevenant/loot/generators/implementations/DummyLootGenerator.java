package com.gestankbratwurst.revenant.projectrevenant.loot.generators.implementations;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.CompoundLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleExpLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootGenerator;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DummyLootGenerator implements LootGenerator {

  @Override
  public Loot apply(Player player) {
    CompoundLoot lootbag = new CompoundLoot();

    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    int roundedHealth = (int) Math.round(revenantPlayer.getBody().getAttribute(BodyAttribute.HEALTH).getCurrentValueModified());
    int itemAmount = roundedHealth / 2;

    lootbag.addLoot(new SimpleExpLoot(roundedHealth));
    lootbag.addLoot(new SimpleItemLoot(new ItemStack(Material.GRASS_BLOCK, itemAmount)));

    return lootbag;
  }
}

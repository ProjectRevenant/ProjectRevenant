package com.gestankbratwurst.revenant.projectrevenant.loot.generators.implementations;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.ChanceLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.CompoundLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleExpLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SoundLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootGenerator;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DummyLootGenerator implements LootGenerator {

  @Override
  public Loot apply(Player player) {
    CompoundLoot lootbag = new CompoundLoot();

    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    int roundedHealth = (int) Math.round(revenantPlayer.getBody().getAttribute(BodyAttribute.HEALTH).getCurrentValueModified());
    int itemAmount = roundedHealth / 4;

    lootbag.addLoot(new ChanceLoot(0.1, new SimpleItemLoot(new ItemStack(Material.STONE, 10))));
    lootbag.addLoot(new ChanceLoot(0.5, new SoundLoot(Sound.ITEM_GOAT_HORN_SOUND_0, 10, 1)));

    lootbag.addLoot(new SimpleExpLoot(roundedHealth));
    lootbag.addLoot(new SimpleItemLoot(new ItemStack(Material.GRASS_BLOCK, itemAmount)));
    lootbag.addLoot(new SimpleItemLoot(RevenantItem.murkyWaterBottle()));

    return lootbag;
  }
}

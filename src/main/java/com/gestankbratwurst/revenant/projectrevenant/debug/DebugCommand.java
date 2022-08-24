package com.gestankbratwurst.revenant.projectrevenant.debug;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageAbortEvent;

import java.util.UUID;

@CommandPermission("admin")
@CommandAlias("debug")
public class DebugCommand extends BaseCommand {

  @Subcommand("getitem")
  @CommandCompletion("@RevenantItem")
  public void onAbilityItem(Player sender, @Values("@RevenantItem") String internalName) {
    sender.getInventory().addItem(RevenantItem.getItemByInternalName(internalName));
    Msg.sendInfo(sender, "Du hast das " + internalName + " item erhalten.");
  }

  @Subcommand("droploot")
  @CommandCompletion("@LootType")
  public void onDropLoot(Player sender, @Values("@LootType") LootType type) {
    type.getGenerator().apply(sender).applyTo(sender, sender.getLocation());
  }

  @Subcommand("lootchest add")
  @CommandCompletion("@LootType")
  public void onLootchestAdd(Player sender, @Values("@LootType") LootType type, int respawnDuration) {
    UUID worldID = sender.getWorld().getUID();
    long chunkID = sender.getChunk().getChunkKey();
    int location = UtilChunk.relativeKeyOf(sender.getLocation().getBlock());
    BlockFace facing = sender.getFacing();

    Chest chestData = (Chest) Bukkit.createBlockData(Material.CHEST);
    chestData.setFacing(facing);

    ProjectRevenant.getLootChestManager().addLootChest(new LootableChest(type, respawnDuration, worldID, chunkID, location, chestData));
  }

  @Subcommand("lootchest remove")
  public void onLootchestRemove(Player sender) {
    Block block = sender.getTargetBlock(10, TargetBlockInfo.FluidMode.NEVER);

    if (block == null) {
      return;
    }

    ProjectRevenant.getLootChestManager().removeLootChest(LootableChest.identityAt(block));
    block.setType(Material.AIR);
  }
}
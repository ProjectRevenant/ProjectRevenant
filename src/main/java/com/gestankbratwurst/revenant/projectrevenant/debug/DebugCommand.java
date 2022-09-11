package com.gestankbratwurst.revenant.projectrevenant.debug;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStationManager;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.DummyStation;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobManager;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobType;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

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

  @Subcommand("custommob")
  @CommandCompletion("@CustomMobType")
  public void onCustomMob(Player sender, @Values("@CustomMobType") CustomMobType type, @Default("1") int amount) {
    for (int i = 0; i < amount; i++) {
      type.spawnAsNms(sender.getLocation());
    }
    Msg.sendInfo(sender, "Du hast {} entities gespawnt. ({})", type, "x" + amount);
  }

  @Subcommand("craftstation add")
  public void onCraftStationSpawn(Player sender) {
    Block stationBlock = sender.getLocation().getBlock();
    stationBlock.setType(Material.CRAFTING_TABLE);

    ProjectRevenant.getCraftingStationManager().createStation(stationBlock, new DummyStation(Position.at(stationBlock)));
  }


  @Subcommand("droploot")
  @CommandCompletion("@LootType")
  public void onDropLoot(Player sender, @Values("@LootType") LootType type) {
    type.getGenerator().apply(sender).applyTo(sender, sender.getLocation());
  }

  @Subcommand("lootchest add")
  @CommandCompletion("@LootType")
  public void onLootchestAdd(Player sender, @Values("@LootType") LootType type) {
    UUID worldID = sender.getWorld().getUID();
    long chunkID = sender.getChunk().getChunkKey();
    int location = UtilChunk.relativeKeyOf(sender.getLocation().getBlock());
    BlockFace facing = sender.getFacing();

    Chest chestData = (Chest) Bukkit.createBlockData(Material.CHEST);
    chestData.setFacing(facing);

    ProjectRevenant.getLootChestManager().addLootChest(new LootableChest(type, new Position(worldID, chunkID, location), chestData));
  }

  @Subcommand("lootchest remove")
  public void onLootchestRemove(Player sender) {
    Block block = sender.getTargetBlock(10, TargetBlockInfo.FluidMode.NEVER);

    if (block == null) {
      return;
    }

    ProjectRevenant.getLootChestManager().removeLootChestAt(Position.at(block));
    block.setType(Material.AIR);
  }
}
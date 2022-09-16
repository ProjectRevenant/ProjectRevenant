package com.gestankbratwurst.revenant.projectrevenant.debug;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.AbstractRecipeStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.CookingStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.DummyStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.FurnaceStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.PlayerCampfireStation;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootChestSpawnArea;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobType;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.util.RayTraceResult;

@CommandPermission("admin")
@CommandAlias("debug")
public class DebugCommand extends BaseCommand {

  @Subcommand("getitem")
  @CommandCompletion("@RevenantItem")
  public void onAbilityItem(Player sender, @Values("@RevenantItem") String internalName, @Default("1") int amount) {
    for (int i = 0; i < amount; i++) {
      sender.getInventory().addItem(RevenantItem.getItemByInternalName(internalName));
    }
    Msg.sendInfo(sender, "Du hast das " + internalName + "x" + amount + " item erhalten.");
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
  @CommandCompletion("campfire|cooking|furnace")
  public void onCraftStationSpawn(Player sender, @Default("campfire") String type) {
    Block stationBlock = sender.getLocation().getBlock();
    stationBlock.setType(Material.CRAFTING_TABLE);

    AbstractRecipeStation station = switch (type) {
      case "campfire" -> new PlayerCampfireStation(Position.at(stationBlock));
      case "cooking" -> new CookingStation(Position.at(stationBlock));
      case "furnace" -> new FurnaceStation(Position.at(stationBlock));
      default -> new DummyStation(Position.at(stationBlock));
    };

    ProjectRevenant.getCraftingStationManager().createStation(stationBlock, station);
  }


  @Subcommand("droploot")
  @CommandCompletion("@LootType")
  public void onDropLoot(Player sender, @Values("@LootType") LootType type) {
    type.getGenerator().apply(sender).applyTo(sender, sender.getLocation());
  }

  @Subcommand("manipulationspot")
  public void onManipulationSpotCreation(Player sender, double scalar){
    ProjectRevenant.getChunkHeatManager().addManipulation(sender.getChunk().getChunkKey(), scalar);
  }

  @Subcommand("createstash")
  public void onStashCreation(Player sender){
    RayTraceResult traceResult = sender.rayTraceBlocks(16);
    if (traceResult == null) {
      Msg.sendError(sender, "Bitte schaue auf einen Block.");
      return;
    }
    Block block = traceResult.getHitBlock();
    if (block == null) {
      Msg.sendError(sender, "Bitte schaue auf einen Block.");
      return;
    }
    BlockState state = block.getState();
    if (!(state instanceof PersistentDataHolder dataHolder)) {
      Msg.sendError(sender, "Der Block muss einen PersistantDataHolder besitzen.");
      return;
    }
    ProjectRevenant.getStashManager().applyStashTo(dataHolder);
    state.update(true);
    Msg.sendInfo(sender, "Stash erstellt.");
  }

  @Subcommand("lootchest addarea")
  public void onLootchestAreaAdd(Player sender, String areaName) {
    LootChestSpawnArea area = new LootChestSpawnArea();
    area.setInternalName(areaName);
    ProjectRevenant.getLootChestManager().addSpawnArea(area);
    Msg.sendInfo(sender, "Du hast die {} Area hinzugefügt.", areaName);
  }

  @Subcommand("lootchest addtype")
  @CommandCompletion("@LootChestArea @LootType")
  public void onLootchestAdd(Player sender, @Values("@LootChestArea") LootChestSpawnArea area, @Values("@LootType") LootType type, double weight) {
    area.addType(type, weight);
    Msg.sendInfo(sender, "Du hast {} mit einem Gewicht von {} hinzugefügt.", type, weight);
  }

  @Subcommand("lootchest addpositoon")
  @CommandCompletion("@LootChestArea")
  public void onLootchestPositionAdd(Player sender, @Values("@LootChestArea") LootChestSpawnArea area) {
    RayTraceResult traceResult = sender.rayTraceBlocks(16);
    if (traceResult == null) {
      Msg.sendError(sender, "Bitte schaue auf einen Block.");
      return;
    }
    Block block = traceResult.getHitBlock();
    if (block == null) {
      Msg.sendError(sender, "Bitte schaue auf einen Block.");
      return;
    }
    if (!(block.getState() instanceof PersistentDataHolder) || !(block.getState() instanceof InventoryHolder)) {
      Msg.sendError(sender, "Der Block muss ein Inventar besitzen.");
      return;
    }

    area.addEmptyPosition(Position.at(block), block.getBlockData());
    block.setType(Material.AIR);
    sender.playSound(block.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.5F);
    Msg.sendInfo(sender, "Position wurde hinzugefügt.");
  }

  @Subcommand("lootchest setmax")
  @CommandCompletion("@LootChestArea")
  public void onLootChestMax(Player sender, @Values("@LootChestArea") LootChestSpawnArea area, int max) {
    area.setMaxActive(max);
    ProjectRevenant.getLootChestManager().populateArea(area);
    Msg.sendInfo(sender, "Die maximale Anzahl an Kisten für {} ist jetzt {}.", area.getInternalName(), max);
  }

  @Subcommand("lootchest populate")
  @CommandCompletion("@LootChestArea")
  public void onLootChestPopulate(Player sender, @Values("@LootChestArea") LootChestSpawnArea area) {
    ProjectRevenant.getLootChestManager().populateArea(area);
    Msg.sendInfo(sender, "{} wurde neu aufgefüllt.", area.getInternalName());
  }

  @Subcommand("lootchest forcepopulateall")
  public void onLootChestPopulate(Player sender) {
    ProjectRevenant.getLootChestManager().forceFullChestPopulation();
    Msg.sendInfo(sender, "Starte vollständige Population...");
  }

  @Subcommand("lootchest info")
  @CommandCompletion("@LootChestArea")
  public void onLootChestSingleInfo(Player sender, @Values("@LootChestArea") LootChestSpawnArea area) {
    sender.sendMessage("§eArea: §f" + area.getInternalName());
    sender.sendMessage("§eLoot spots:§f [" + area.getCurrentActive() + "/" + area.getMaxActive() + "]");
    sender.sendMessage("§eIntegrity Check: " + ((area.getMaxActive() - area.getCurrentActive() - area.getEnqueudCount()) == 0 ? "§aOK" : "§cFAULTY"));
  }

  @Subcommand("lootchest infolist")
  public void onLootChestListInfo(Player sender) {
    sender.sendMessage("§6Alle Areas:");
    for (LootChestSpawnArea area : ProjectRevenant.getLootChestManager().getAllAreas()) {
      String integrity = ((area.getMaxActive() - area.getCurrentActive() - area.getEnqueudCount()) == 0 ? "§aOK" : "§cFAULTY");
      sender.sendMessage("§e- §f" + area.getInternalName() + " [" + area.getCurrentActive() + "/" + area.getMaxActive() + "] " + integrity);
    }
  }

  @Subcommand("lootchest remove")
  @CommandCompletion("@LootChestArea")
  public void onLootChestAreaRemove(Player sender, @Values("@LootChestArea") LootChestSpawnArea area) {
    ProjectRevenant.getLootChestManager().removeLootChestArea(area);
    Msg.sendInfo(sender, "Lösche LootSpawnArea...");
  }

  @Subcommand("playerspawn addpod")
  public void onAddPod(Player sender) {
    ProjectRevenant.getPlayerSpawnManager().addSpawnPod(Position.at(sender.getLocation()));
    Msg.sendInfo(sender, "Du hast einen Pod hinzugefügt.");
  }

  @Subcommand("playerspawn addspawn")
  public void onAddSpawn(Player sender) {
    ProjectRevenant.getPlayerSpawnManager().addSpawnPoint(Position.at(sender.getLocation()));
    Msg.sendInfo(sender, "Du hast einen Spawnpunkt hinzugefügt.");
  }

  @Subcommand("playerspawn removepod")
  public void onRemovePod(Player sender) {
    ProjectRevenant.getPlayerSpawnManager().removeSpawnPod(Position.at(sender.getLocation()));
    Msg.sendInfo(sender, "Du hast einen Pod gelöscht. (Falls hier einer war...)");
  }

  @Subcommand("playerspawn removespawn")
  public void onRemoveSpawn(Player sender) {
    ProjectRevenant.getPlayerSpawnManager().removeSpawnPoint(Position.at(sender.getLocation()));
    Msg.sendInfo(sender, "Du hast einen Spawnpunkt gelöscht. (Falls hier einer war...)");
  }


}
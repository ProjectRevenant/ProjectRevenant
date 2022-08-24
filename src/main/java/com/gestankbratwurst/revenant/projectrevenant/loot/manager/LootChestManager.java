package com.gestankbratwurst.revenant.projectrevenant.loot.manager;

import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement.ChunkDomain;
import com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement.WorldDomain;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.type.Chest;

import java.io.Flushable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LootChestManager implements Flushable {

  private final transient Map<UUID, WorldDomain<ChunkDomain<LootableChest>>> spawnableLootChests = new HashMap<>();
  private final Set<LootableChest> allChests = new HashSet<>();

  public void addLootChest(LootableChest chest) {
    allChests.add(chest);
    enqueChestForRespawn(chest);
  }

  public void initialize() {
    allChests.forEach(this::enqueChestForRespawn);
  }

  public void checkRespawnableChestsInChunk(Chunk chunk) {
    UUID worldId = chunk.getWorld().getUID();
    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.get(worldId);

  }

  public void enqueChestForRespawn(LootableChest lootableChest) {
    UUID worldId = lootableChest.getWorldUUID();
    long chunkKey = lootableChest.getChunkID();
    int position = lootableChest.getLocationInChunk();

    World world = Bukkit.getWorld(worldId);

    if (world != null) {
      //potential null pointer
      Chunk chunk = world.getChunkAt(chunkKey);
      if (chunk.isLoaded()) {
        spawnLootableChest(lootableChest, false);
      }
    }

    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.get(worldId);


    if(worldDomain == null){

      WorldDomain<ChunkDomain<LootableChest>> newWorldDomain = new WorldDomain<>();
      ChunkDomain<LootableChest> newChunkDomain = new ChunkDomain<LootableChest>();
      newChunkDomain.addAtLocation(position, lootableChest);
      newWorldDomain.addInChunk(chunkKey, newChunkDomain);

      spawnableLootChests.put(worldId, newWorldDomain);

    } else {
      ChunkDomain<LootableChest> chunkDomain = worldDomain.getInChunk(chunkKey);

      if(chunkDomain == null){
        ChunkDomain<LootableChest> newChunkDomain = new ChunkDomain<LootableChest>();
        newChunkDomain.addAtLocation(position, lootableChest);
        worldDomain.addInChunk(chunkKey, newChunkDomain);

      } else {

        chunkDomain.addAtLocation(position, lootableChest);

      }

    }

  }

  public void dequeChestFromRespawn(LootableChest lootableChest) {
    UUID worldId = lootableChest.getWorldUUID();
    long chunkKey = lootableChest.getChunkID();
    int position = lootableChest.getLocationInChunk();

    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.get(worldId);

    if (worldDomain == null) {
      return;
    }

    ChunkDomain<LootableChest> chunkDomain = worldDomain.getInChunk(chunkKey);

    if (chunkDomain == null) {
      return;
    }

    chunkDomain.removeAtLocation(position);

    if (chunkDomain.isEmpty()) {
      worldDomain.removeInChunk(chunkKey);
      if (worldDomain.isEmpty()) {
        spawnableLootChests.remove(worldId);
      }
    }
  }

  /**
   *
   * @param lootableChest Chest that is to be spawned
   * @param removeFromList Set to false if the chest is not in spawnableLootChests
   */
  public void spawnLootableChest(LootableChest lootableChest, boolean removeFromList) {
    LootManager lootManager = LootManager.getInstance();

    World world = Bukkit.getWorld(lootableChest.getWorldUUID());
    Chunk chunk = world.getChunkAt(lootableChest.getChunkID());
    int position = lootableChest.getLocationInChunk();

    Block block = chunk.getBlock(UtilChunk.blockKeyToX(position), UtilChunk.blockKeyToY(position), UtilChunk.blockKeyToZ(position));

    Chest blockData = (Chest) Bukkit.createBlockData(Material.CHEST);
    blockData.setFacing(lootableChest.getDirection());
    block.setBlockData(blockData, true);

    TileState state = (TileState) block.getState();
    lootManager.applyTypeTo(state, lootableChest.getType());
    state.update(true);

    if(removeFromList){
      dequeChestFromRespawn(lootableChest);
    }

  }


  public void save() {

  }

  public void load() {

  }

  @Override
  public void flush() {
    save();
  }
}
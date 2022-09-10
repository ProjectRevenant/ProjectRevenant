package com.gestankbratwurst.revenant.projectrevenant.crafting.station;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.blockdata.BlockDataManager;
import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class CraftingStationManager {

  private final NamespacedKey stationDataKey = NamespaceFactory.provide("station-data");
  private final Map<Position, CraftingStation> loadedStations = new HashMap<>();

  public void init() {
    for (World world : Bukkit.getWorlds()) {
      for (Chunk chunk : world.getLoadedChunks()) {
        initChunk(chunk);
      }
    }
  }

  public void terminate() {
    for (World world : Bukkit.getWorlds()) {
      for (Chunk chunk : world.getLoadedChunks()) {
        terminateChunk(chunk);
      }
    }
  }

  public void tickStations() {
    loadedStations.values().forEach(CraftingStation::tick);
  }

  public void createStation(Block block, CraftingStation station) {
    BlockDataManager blockDataManager = MMCore.getBlockDataManager();
    PersistentDataContainer container = blockDataManager.getOrCreateData(block);
    container.set(stationDataKey, PersistentDataType.STRING, MMCore.getGsonProvider().toJson(station));
    addLoadedStation(block, station);
  }

  private void addLoadedStation(Block block, CraftingStation station) {
    loadedStations.put(Position.at(block), station);
  }

  private CraftingStation removeLoadedBlock(Block block) {
    return loadedStations.remove(Position.at(block));
  }

  public void initChunk(Chunk chunk) {
    BlockDataManager blockDataManager = MMCore.getBlockDataManager();
    Map<Block, PersistentDataContainer> data = blockDataManager.getDataInChunk(chunk);
    data.forEach((block, container) -> {
      if (container.has(stationDataKey)) {
        addLoadedStation(block, MMCore.getGsonProvider().fromJson(container.get(stationDataKey, PersistentDataType.STRING), CraftingStation.class));
      }
    });
  }

  public void terminateChunk(Chunk chunk) {
    BlockDataManager blockDataManager = MMCore.getBlockDataManager();
    Map<Block, PersistentDataContainer> data = blockDataManager.getDataInChunk(chunk);
    data.forEach((block, container) -> {
      if (container.has(stationDataKey)) {
        CraftingStation station = removeLoadedBlock(block);
        container.set(stationDataKey, PersistentDataType.STRING, MMCore.getGsonProvider().toJson(station));
      }
    });
  }

}

package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.ChunkHeatManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChunkHeatLoot implements Loot {

  private static final double linearDropoff = 0.75;
  private final ChunkHeatManager chunkHeatManager;
  private final double amount;
  private final int radius;

  public ChunkHeatLoot(double amount, int radius) {
    this.chunkHeatManager = ProjectRevenant.getChunkHeatManager();
    this.amount = amount;
    this.radius = radius;
  }

  @Override
  public void applyTo(Player looter, Location location) {
    Chunk chunk = location.getChunk();
    int chunkX = chunk.getX();
    int chunkZ = chunk.getZ();
    chunkHeatManager.addHeat(chunk.getChunkKey(), amount);

    for (int xOff = -radius; xOff <= radius; xOff++) {
      for (int zOff = -radius; zOff <= radius; zOff++) {
        long offsetKey = Chunk.getChunkKey(chunkX + xOff, chunkZ + zOff);
        chunkHeatManager.addHeat(offsetKey, amount / ((Math.abs(xOff) + Math.abs(zOff)) * linearDropoff));
      }
    }
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    this.applyTo(looter, looter.getLocation());
  }

}

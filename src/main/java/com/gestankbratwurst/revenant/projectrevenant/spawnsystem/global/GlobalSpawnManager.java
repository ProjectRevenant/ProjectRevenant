package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobType;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GlobalSpawnManager {

  private static final int maxMonstersPerPlayer = 25;
  private static final int spawnLocationIterations = 5;
  private static final int minDistance = 30;
  private static final int maxDistance = 70;
  private static final int maxYDif = 10;
  private static final double highestVsOffsetChance = 0.4;
  static final double discardHighestBlock = 0.25;

  private final Map<Player, Integer> playerMonsterCount = new HashMap<>();
  private final Map<UUID, Player> monsterPlayerAssignment = new ConcurrentHashMap<>(2048);

  /**
   * @param player target player
   * @param amount positive amount for positive change, negative amount for negative change
   */
  public void changePlayerMonsterCount(Player player, int amount) {
    playerMonsterCount.compute(player, (key, currentAmount) -> currentAmount == null ? amount : currentAmount + amount);
  }

  public void assignMonsterToPlayer(UUID uuid, Player player) {
    monsterPlayerAssignment.put(uuid, player);
  }

  public void removeMonsterAssignment(UUID uuid) {
    Player player = monsterPlayerAssignment.get(uuid);

    if (player == null) {
      return;
    }

    playerMonsterCount.compute(player, (key, amount) -> amount == null ? 0 : amount - 1);

    monsterPlayerAssignment.remove(uuid);
  }

  public void spawnForNext() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      spawnMonsters(player);
    }
  }

  public void spawnMonsters(Player target) {

    if(!(target.getGameMode() == GameMode.SURVIVAL || target.getGameMode() == GameMode.ADVENTURE)){
      return;
    }

    int current = playerMonsterCount.computeIfAbsent(target, (key) -> 0);

    if (current >= maxMonstersPerPlayer) {
      return;
    }

    Location currentLocation = target.getLocation();
    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    for (int i = 0; i < spawnLocationIterations; i++) {

      int xOffset = threadLocalRandom.nextInt(minDistance, maxDistance);
      int zOffset = threadLocalRandom.nextInt(minDistance, maxDistance);

      if(threadLocalRandom.nextBoolean()) xOffset = -xOffset;
      if(threadLocalRandom.nextBoolean()) zOffset = -zOffset;

      Location offsetLocation = currentLocation.clone().add(xOffset, 0, zOffset);

      int chunkX = offsetLocation.getBlockX() >> 4;
      int chunkZ = offsetLocation.getBlockZ() >> 4;

      if (!offsetLocation.getWorld().isChunkLoaded(chunkX, chunkZ)) {
        continue;
      }

      Block highestBlock = offsetLocation.getWorld().getHighestBlockAt(offsetLocation);
      Location spawnableLocation = getViableLocation(threadLocalRandom, offsetLocation, highestBlock);

      if (spawnableLocation == null) {
        continue;
      }

      CustomMobType type = computeMonsterForLocation(spawnableLocation);

      if (type == null) {
        continue;
      }

      Location finalSpawnableLocation = spawnableLocation.add(0.5, 1, 0.5);

      Entity spawned = computeMonsterForLocation(finalSpawnableLocation).spawnAsNms(finalSpawnableLocation);

      monsterPlayerAssignment.put(spawned.getUUID(), target);

      playerMonsterCount.compute(target, (player, currentValue) -> currentValue == null ? 0 : currentValue + 1);

    }


  }

  @Nullable
  private Location getViableLocation(ThreadLocalRandom threadLocalRandom, Location offsetLocation, Block highestBlock) {
    if (spawnPossible(highestBlock) && threadLocalRandom.nextDouble() <= discardHighestBlock) {
      //spawnableLocation = the highest Block
      return highestBlock.getLocation();
    }

    Block randomBlock = offsetLocation.getBlock();

    if (spawnPossible(randomBlock)) {
      //spawnableLocation = block at random offset
      return offsetLocation;
    }


    if (threadLocalRandom.nextDouble() <= highestVsOffsetChance) {
      for (int j = 0; j < maxYDif; j++) {
        Block offsetBlock = highestBlock.getRelative(0, -j, 0);
        if (spawnPossible(offsetBlock)) {
          //spawnableLocation = first spawnable block beneath the highest block
          return offsetBlock.getLocation();
        }
      }
      return null;
    }

    boolean up = threadLocalRandom.nextDouble() >= 0.5;

    for (int j = 0; j < maxYDif; j++) {
      Block offsetBlock = highestBlock.getRelative(0, up ? j : -j, 0);
      if (spawnPossible(offsetBlock)) {
        //spawnableLocation = first spawnable block going up or down from the random block
        return offsetBlock.getLocation();
      }
    }

    return null;
  }

  public CustomMobType computeMonsterForLocation(Location spawnableLocation) {
    return CustomMobType.REVENANT_ZOMBIE;
  }

  public boolean spawnPossible(Block block) {
    if (!block.isSolid() || !block.getType().isOccluding()) {
      return false;
    }

    return block.getRelative(0, 1, 0).getType() == Material.AIR && block.getRelative(0, 1, 0).getType() == Material.AIR;
  }
}

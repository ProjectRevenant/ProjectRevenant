package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class LootableChest implements Comparable<LootableChest> {

  @Data
  @AllArgsConstructor
  public static class Position {

    public static Position at(Location location) {
      return Position.at(location.getBlock());
    }

    public static Position at(Block block) {
      UUID worldId = block.getWorld().getUID();
      long chunkId = UtilChunk.getChunkKey(block.getChunk());
      int relLoc = UtilChunk.relativeKeyOf(block);
      return new Position(worldId, chunkId, relLoc);
    }

    private final UUID worldId;
    private final long chunkId;
    private final int relLoc;
  }

  private final LootType type;
  private final Position position;
  private final BlockData blockData;
  private long respawnTimestamp = 0L;

  @Override
  public int hashCode() {
    return position.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (!(obj instanceof LootableChest lootChest)) {
      return false;
    }

    return this.position.equals(lootChest.position);
  }

  public void setRespawnTimestamp(long respawnTimestamp) {
    this.respawnTimestamp = respawnTimestamp;
  }

  public void setRespawnTimeFromNow() {
    this.setRespawnTimestamp(System.currentTimeMillis() + type.getRespawnTimeMillis());
  }

  @Override
  public int compareTo(@NotNull LootableChest other) {
    return Long.compare(this.respawnTimestamp, other.respawnTimestamp);
  }
}
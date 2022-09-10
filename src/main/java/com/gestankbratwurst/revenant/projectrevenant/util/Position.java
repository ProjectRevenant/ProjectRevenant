package com.gestankbratwurst.revenant.projectrevenant.util;

import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Position {

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

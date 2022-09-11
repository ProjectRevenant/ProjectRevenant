package com.gestankbratwurst.revenant.projectrevenant.util;

import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
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

  public Location toLocation() {
    World world = Bukkit.getWorld(worldId);
    if(world == null) {
      return null;
    }
    int[] coords = UtilChunk.getChunkCoords(chunkId);
    int x = (coords[0] << 4) + UtilChunk.blockKeyToX(relLoc);
    int y = UtilChunk.blockKeyToY(relLoc);
    int z = (coords[1] << 4) + UtilChunk.blockKeyToZ(relLoc);
    return new Location(world, x, y, z);
  }
}

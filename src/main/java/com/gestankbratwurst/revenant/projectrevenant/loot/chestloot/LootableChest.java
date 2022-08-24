package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class LootableChest {

  public static LootableChest identityAt(Block block) {
    UUID worldId = block.getWorld().getUID();
    long chunkId = UtilChunk.getChunkKey(block.getChunk());
    int relLoc = UtilChunk.relativeKeyOf(block);
    return new LootableChest(LootType.DUMMY_LOOT, 0, worldId, chunkId, relLoc, null);
  }

  private final LootType type;
  private final long respawnTime;
  private final UUID worldUUID;
  private final long chunkID;
  private final int locationInChunk;
  private BlockData blockData;

  @Override
  public int hashCode() {
    return Objects.hash(worldUUID, chunkID, locationInChunk);
  }

  @Override
  public boolean equals(Object obj) {

    if (!(obj instanceof LootableChest lootChest)) {
      return false;
    }

    return lootChest.locationInChunk == locationInChunk && lootChest.chunkID == chunkID && lootChest.worldUUID.equals(worldUUID);
  }
}
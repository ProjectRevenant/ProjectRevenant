package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.BlockFace;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LootableChest {

  private final LootType type;
  private final long respawnTime;
  private final UUID worldUUID;
  private final Long chunkID;
  private final Integer locationInChunk;
  private BlockFace direction;

}
package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DangerLevel {

  VERY_LOW("Sehr niedrig", TextureModel.ZOMBIE_LEVEL_1, 0),
  LOW("Niedrig", TextureModel.ZOMBIE_LEVEL_2, 50),
  AVERAGE("Normal", TextureModel.ZOMBIE_LEVEL_3, 200),
  MEDIUM("Leicht erhöht", TextureModel.ZOMBIE_LEVEL_4, 400),
  INCREASED("Erhöht", TextureModel.ZOMBIE_LEVEL_5, 750),
  HIGHLY_INCREASED("Stark erhöht", TextureModel.ZOMBIE_LEVEL_6, 2000),
  HIGH("Hoch", TextureModel.ZOMBIE_LEVEL_7, 5000),
  VERY_HIGH("Sehr hoch", TextureModel.ZOMBIE_LEVEL_8, 17500),
  EXTREME("Extrem hoch", TextureModel.ZOMBIE_LEVEL_9, 25000),
  INSANE("Wahnsinnig hoch", TextureModel.ZOMBIE_LEVEL_10, 50000);

  @Getter
  private final String displayName;
  @Getter
  private final TextureModel textureModel;
  @Getter
  private final double heatThreshold;

  public static DangerLevel getByHeat(double heat) {
    DangerLevel[] levels = DangerLevel.values();
    for(int i = levels.length -1; i >= 0; i--) {
      if(heat >= levels[i].heatThreshold) {
        return levels[i];
      }
    }
    return DangerLevel.VERY_LOW;
  }

}

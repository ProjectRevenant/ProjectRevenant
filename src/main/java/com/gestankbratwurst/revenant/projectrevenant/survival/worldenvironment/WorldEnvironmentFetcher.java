package com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class WorldEnvironmentFetcher {

  private static final RangeMap<Integer, TextureModel> thermometerMap = TreeRangeMap.create();
  private static final Set<Biome> noRainBiomes = Set.of(
          Biome.DESERT,
          Biome.BADLANDS,
          Biome.WOODED_BADLANDS,
          Biome.ERODED_BADLANDS,
          Biome.SAVANNA,
          Biome.SAVANNA_PLATEAU,
          Biome.WINDSWEPT_SAVANNA
  );
  private static final Map<Biome, WaterType> waterTypeMap = Map.copyOf(
          new HashMap<>() {{
            put(Biome.SNOWY_BEACH, WaterType.SALTY);
            put(Biome.STONY_SHORE, WaterType.SALTY);
            put(Biome.SWAMP, WaterType.MURKY);
            put(Biome.MANGROVE_SWAMP, WaterType.MURKY);
            put(Biome.BEACH, WaterType.SALTY);
            put(Biome.RIVER, WaterType.MURKY);
            put(Biome.FROZEN_RIVER, WaterType.CLEAR);
            put(Biome.WARM_OCEAN, WaterType.SALTY);
            put(Biome.LUKEWARM_OCEAN, WaterType.SALTY);
            put(Biome.DEEP_LUKEWARM_OCEAN, WaterType.SALTY);
            put(Biome.OCEAN, WaterType.SALTY);
            put(Biome.DEEP_OCEAN, WaterType.SALTY);
            put(Biome.COLD_OCEAN, WaterType.SALTY);
            put(Biome.DEEP_COLD_OCEAN, WaterType.SALTY);
            put(Biome.FROZEN_OCEAN, WaterType.SALTY);
            put(Biome.DEEP_FROZEN_OCEAN, WaterType.SALTY);
            put(Biome.LUSH_CAVES, WaterType.MURKY);
          }}
  );
  private static final Set<Material> hotBlocks = Set.of(
          Material.CAMPFIRE,
          Material.LAVA,
          Material.LAVA_CAULDRON,
          Material.MAGMA_BLOCK
  );

  static {
    thermometerMap.put(Range.atMost(0), TextureModel.THERMOMETER_0);
    thermometerMap.put(Range.openClosed(0, 9), TextureModel.THERMOMETER_9);
    thermometerMap.put(Range.openClosed(9, 16), TextureModel.THERMOMETER_16);
    thermometerMap.put(Range.openClosed(16, 23), TextureModel.THERMOMETER_23);
    thermometerMap.put(Range.openClosed(23, 30), TextureModel.THERMOMETER_30);
    thermometerMap.put(Range.openClosed(30, 37), TextureModel.THERMOMETER_37);
    thermometerMap.put(Range.openClosed(37, 44), TextureModel.THERMOMETER_44);
    thermometerMap.put(Range.openClosed(44, 51), TextureModel.THERMOMETER_51);
    thermometerMap.put(Range.openClosed(51, 58), TextureModel.THERMOMETER_58);
    thermometerMap.put(Range.openClosed(58, 65), TextureModel.THERMOMETER_65);
    thermometerMap.put(Range.openClosed(65, 72), TextureModel.THERMOMETER_72);
    thermometerMap.put(Range.openClosed(72, 79), TextureModel.THERMOMETER_79);
    thermometerMap.put(Range.openClosed(79, 86), TextureModel.THERMOMETER_86);
    thermometerMap.put(Range.openClosed(86, 93), TextureModel.THERMOMETER_93);
    thermometerMap.put(Range.atLeast(94), TextureModel.THERMOMETER_100);
  }

  public static WaterType getWaterTypeAt(Location location) {
    return waterTypeMap.getOrDefault(location.getWorld().getBiome(location), WaterType.CLEAR);
  }

  public static TextureModel getThermometer(double current, double lowerBound, double upperBound) {
    int value = (int) (100.0 / (upperBound - lowerBound) * (current - lowerBound));
    return thermometerMap.get(value);
  }

  public static double getTemperatureAt(Location location, boolean applyHeatSources) {
    Block block = location.getBlock();
    double baseTemp = location.getBlock().getTemperature() - 0.15;
    double temp = baseTemp * 30.0;
    Biome biome = block.getBiome();

    if(noRainBiomes.contains(biome)) {
      temp -= (isDay(block.getWorld()) ? 0.0 : 40.0);
    } else {
      temp -= (block.getWorld().hasStorm() ? 5.0 : 0.0) - (isDay(block.getWorld()) ? 0.0 : 7.5);
    }
    if(applyHeatSources && isNearHeatSource(location)) {
      if(temp < 5) {
        temp += 15;
      } else {
        temp += 7.5;
      }
    }
    return temp;
  }

  public static boolean isDry(Location location, boolean applyHeatSources) {
    double temp = getTemperatureAt(location, applyHeatSources);
    if(temp <= 12.5) {
      return false;
    }
    World world = location.getWorld();
    if(!isDay(world)) {
      return false;
    }
    return location.getBlock().getLightLevel() > 13 || location.getBlock().getRelative(BlockFace.DOWN).getLightLevel() > 13;
  }

  public static boolean isNearHeatSource(Location location) {
    Block base = location.getBlock();
    for(int x = -2; x <= 2; x++) {
      for(int z = -2; z <= 2; z++) {
        for(int y = -1; y <= 1; y++) {
          Block relative = base.getRelative(x, y, z);
          if(hotBlocks.contains(relative.getType())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static boolean isDay(World world) {
    long time = world.getTime();
    return !(time < 12300 || time > 23850);
  }

  public static double getHumidityAt(Location location) {
    return location.getBlock().getHumidity() / 4.0;
  }

}

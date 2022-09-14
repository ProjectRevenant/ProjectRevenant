package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class BodyAttributeIcon {

  private static final Map<String, RangeMap<Integer, TextureModel>> attributeIcons = new HashMap<>();
  private static boolean initialized = false;
  private static final String[] magicKeys = {
          "HEALTH_BAR",
          "FOOD_BAR",
          "WATER_BAR",
          "WEIGHT_BAR",
          "HEALTH_SHIFT",
          "WEIGHT_SHIFT_AS",
          "FOOD_SHIFT",
          "WATER_SHIFT_AS",
          "TEMPERATURE_SHIFT"
  };

  private static Map<String, RangeMap<Integer, TextureModel>> getIcons() {
    if (!initialized) {
      List<String> ids = new ArrayList<>(Arrays.asList(magicKeys));
      for (String identifier : ids) {
        RangeMap<Integer, TextureModel> rangeMap = TreeRangeMap.create();
        attributeIcons.put(identifier, rangeMap);
        if (identifier.contains("SHIFT")) {
          if (identifier.contains("AS")) {
            rangeMap.put(Range.atLeast(60), TextureModel.GRADIENT_ICON_3_AS);
            rangeMap.put(Range.closedOpen(20, 60), TextureModel.GRADIENT_ICON_2_AS);
            rangeMap.put(Range.open(0, 20), TextureModel.GRADIENT_ICON_1_AS);
            rangeMap.put(Range.singleton(0), TextureModel.GRADIENT_ICON_0_AS);
            rangeMap.put(Range.atMost(-60), TextureModel.GRADIENT_ICON_M3_AS);
            rangeMap.put(Range.openClosed(-60, -20), TextureModel.GRADIENT_ICON_M2_AS);
            rangeMap.put(Range.open(-20, 0), TextureModel.GRADIENT_ICON_M1_AS);
          } else {
            rangeMap.put(Range.atLeast(60), TextureModel.GRADIENT_ICON_3);
            rangeMap.put(Range.closedOpen(20, 60), TextureModel.GRADIENT_ICON_2);
            rangeMap.put(Range.open(0, 20), TextureModel.GRADIENT_ICON_1);
            rangeMap.put(Range.singleton(0), TextureModel.GRADIENT_ICON_0);
            rangeMap.put(Range.atMost(-60), TextureModel.GRADIENT_ICON_M3);
            rangeMap.put(Range.openClosed(-60, -20), TextureModel.GRADIENT_ICON_M2);
            rangeMap.put(Range.open(-20, 0), TextureModel.GRADIENT_ICON_M1);
          }
        } else {
          for (int i = 0; i <= 100; i++) {
            String modelId = identifier.toUpperCase() + "_" + i;
            TextureModel model;
            try {
              model = TextureModel.valueOf(modelId);
            } catch (Exception exception) {
              continue;
            }
            if (i == 0) {
              rangeMap.put(Range.atMost(0), model);
            } else if (i == 100) {
              rangeMap.put(Range.atLeast(100), model);
            } else {
              rangeMap.put(Range.closedOpen(i, i + 1), model);
            }
          }
        }
      }
      initialized = true;
    }
    return attributeIcons;
  }

  public static TextureModel of(String identity, int percentageFull) {
    return Optional.ofNullable(getIcons().get(identity).get(percentageFull)).orElse(TextureModel.RED_X);
  }

  public static TextureModel of(String identity, double percentageScaled) {
    return of(identity, (int) (percentageScaled * 100.0));
  }

}

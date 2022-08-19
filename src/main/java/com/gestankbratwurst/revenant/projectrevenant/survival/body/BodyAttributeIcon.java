package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class BodyAttributeIcon {

  private static final Map<String, RangeMap<Integer, TextureModel>> attributeIcons = new HashMap<>();
  private static boolean initialized = false;

  private static Map<String, RangeMap<Integer, TextureModel>> getIcons() {
    if (!initialized) {
      for (String identifier : BodyAttribute.getValues()) {
        RangeMap<Integer, TextureModel> rangeMap = TreeRangeMap.create();
        attributeIcons.put(identifier, rangeMap);
        if (identifier.contains("shift")) {
          rangeMap.put(Range.atLeast(75), TextureModel.GRADIENT_ICON_3);
          rangeMap.put(Range.closedOpen(25, 75), TextureModel.GRADIENT_ICON_2);
          rangeMap.put(Range.open(0, 25), TextureModel.GRADIENT_ICON_1);
          rangeMap.put(Range.singleton(0), TextureModel.GRADIENT_ICON_0);
          rangeMap.put(Range.atMost(-75), TextureModel.GRADIENT_ICON_M3);
          rangeMap.put(Range.openClosed(-75, -25), TextureModel.GRADIENT_ICON_M2);
          rangeMap.put(Range.open(-25, 0), TextureModel.GRADIENT_ICON_M1);
        } else {
          for (int i = 0; i <= 100; i += 4) {
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
              rangeMap.put(Range.closedOpen(i, i + 4), model);
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
    return Optional.ofNullable(getIcons().get(identity).get((int) (percentageScaled * 100.0))).orElse(TextureModel.RED_X);
  }

}

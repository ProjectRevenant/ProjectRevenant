package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import org.bukkit.attribute.AttributeModifier;

import java.util.UUID;

public class BodyVanillaMods {

  public static final AttributeModifier OVERWEIGHT_MOD;

  static {
    UUID overweightId = UUID.fromString("51314054-a612-4e4b-a2ff-425f847f66bc");
    String overweightName = "OVERWEIGHT_MOD";
    double overweightMod = -0.88;
    AttributeModifier.Operation overweightOp = AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    OVERWEIGHT_MOD = new AttributeModifier(overweightId, overweightName, overweightMod, overweightOp);
  }

}

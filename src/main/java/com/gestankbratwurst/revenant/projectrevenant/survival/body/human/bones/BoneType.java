package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

public class BoneType {

  public static final String LEFT_ARM = ArmBone.LEFT;
  public static final String RIGHT_ARM = ArmBone.RIGHT;
  public static final String LEFT_LEG = LegBone.LEFT;
  public static final String RIGHT_LEG = ArmBone.RIGHT;
  public static final String RIBS = RibsBone.TYPE;
  public static final String SKULL = SkullBone.TYPE;

  public static String[] values() {
    return new String[]{
            LEFT_ARM,
            RIGHT_ARM,
            LEFT_LEG,
            RIGHT_LEG,
            RIBS,
            SKULL
    };
  }

}

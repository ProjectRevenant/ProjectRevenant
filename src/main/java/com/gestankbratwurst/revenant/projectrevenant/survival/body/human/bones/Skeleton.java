package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Skeleton implements Iterable<Bone> {

  private final Map<String, Bone> boneMap = new HashMap<>();

  public Bone getBone(String boneType) {
    return boneMap.get(boneType);
  }

  public void addBone(Bone bone) {
    boneMap.put(bone.getBoneType(), bone);
  }

  @NotNull
  @Override
  public Iterator<Bone> iterator() {
    return boneMap.values().iterator();
  }
}

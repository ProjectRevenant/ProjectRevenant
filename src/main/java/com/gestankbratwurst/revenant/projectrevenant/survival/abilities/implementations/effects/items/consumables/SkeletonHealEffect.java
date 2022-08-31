package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Skeleton;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.List;
import java.util.Map;

public class SkeletonHealEffect extends AbilityEffect<PlayerItemConsumeEvent> {

  @Getter
  private final Map<String, Boolean> affected;

  /**
   * @param affectedBones Maps bone-string to boolean. Healed if true, bandaged if false
   */

  public SkeletonHealEffect(Map<String, Boolean> affectedBones) {
    super(AbilityTrigger.CONSUME_ITEM);
    this.affected = affectedBones;
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    RevenantPlayer player = RevenantPlayer.of(element.getPlayer());
    Skeleton skeleton = player.getBody().getSkeleton();
    for (String bone : affected.keySet()) {
      if(affected.get(bone)){
        skeleton.getBone(bone).healBone();
      } else {
        skeleton.getBone(bone).bandageBone();
      }
    }
  }
}

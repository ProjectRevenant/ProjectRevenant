package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.SkeletonHealEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Skeleton;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkeletonHealAbility extends Ability {

  public SkeletonHealAbility() {
    this(new HashMap<>());
  }

  public SkeletonHealAbility(Map<String, Boolean> affectedBones) {
    this.addEffect(new SkeletonHealEffect(affectedBones));
  }

  public SkeletonHealAbility(List<String> affected, boolean healAll){
    Map<String, Boolean> affectedMap = new HashMap<>();
    affected.forEach(affectedBone -> affectedMap.put(affectedBone, healAll));
    this.addEffect(new SkeletonHealEffect(affectedMap));
  }

  private Map<String, Boolean> getAffected() {
    return getEffect(SkeletonHealEffect.class).getAffected();
  }

  @Override
  public boolean shouldDisplayInTab() {
    return false;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return false;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text("§9Heilt");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    List<Component> output = new ArrayList<>();
    Map<String, Boolean> affected = getAffected();

    Skeleton skeleton = RevenantPlayer.of(viewer).getBody().getSkeleton();

    //List<String> bones = affected.keySet().stream().map(skeleton::getBone).map(Bone::getBoneDisplayName).toList();

    //ToDo @Flo die Foreach benötigt den BONE, die Anzeige möchte den DISPLAYNAME, aber ohne Duplikate. Viel Spaß...

    for (String bone : affected.keySet()) {
      if (affected.get(bone)) {
        output.add(Component.text(String.format("§7Heilt einen §e%s-Bruch", bone)));
      } else {
        output.add(Component.text(String.format("§7Bandagiert einen §e%s-Bruch", bone)));
      }
    }

    return output;
  }

  @Override
  public String getPlainTextName() {
    return null;
  }

  @Override
  public TextureModel getModel() {
    return null;
  }

}

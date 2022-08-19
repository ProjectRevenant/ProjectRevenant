package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluationRegistry;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluator;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Bone;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerAbilityEvaluator extends AbilityEvaluator<Player> {
  public PlayerAbilityEvaluator() {
    super(Player.class);
  }

  @Override
  public List<Ability> evaluate(Player target) {
    List<Ability> abilities = new ArrayList<>();
    AbilityEvaluator<ItemStack> itemEvaluator = AbilityEvaluationRegistry.getTyped(ItemStack.class).orElseThrow();
    EntityEquipment equipment = target.getEquipment();
    for (EquipmentSlot slot : EquipmentSlot.values()) {
      abilities.addAll(itemEvaluator.evaluate(equipment.getItem(slot)));
    }
    RevenantPlayer revenantPlayer = RevenantPlayer.of(target);
    abilities.addAll(revenantPlayer.getActiveAbilities());

    HumanBody body = revenantPlayer.getBody();

    for(Bone bone : body.getSkeleton()) {
      abilities.addAll(bone.getEffects());
    }

    return abilities;
  }

}

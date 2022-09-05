package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.mobs.implementations.RevenantZombie;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AbilityLoot implements Loot {

  private final Ability ability;

  public AbilityLoot(Ability ability){
    this.ability = ability;
  }

  @Override
  public void applyTo(Player looter, Location location) {

  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    RevenantPlayer.of(looter).getBody();
  }
}

package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.mobs.implementations.RevenantZombie;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AbilityLoot implements Loot {

  private final Ability ability;
  private final SoundLoot soundLoot;

  public AbilityLoot(Ability ability){
    this(ability, null);
  }
  public AbilityLoot(Ability ability, SoundLoot sound){
    this.ability = ability;
    this.soundLoot = sound;
  }

  @Override
  public void applyTo(Player looter, Location location) {
    applyTo(looter, looter.getInventory());
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    if(ability instanceof TimedAbility timed){
      timed.start();
    }

    if(soundLoot != null){
      soundLoot.applyTo(looter, inventory);
    }

    RevenantPlayer.of(looter).addAbility(ability);
  }
}

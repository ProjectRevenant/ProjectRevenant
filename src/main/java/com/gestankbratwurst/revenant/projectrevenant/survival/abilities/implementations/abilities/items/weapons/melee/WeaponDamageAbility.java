package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.melee;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.AttackSpeedEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.WeaponDamageEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class WeaponDamageAbility extends Ability {

  public WeaponDamageAbility() {
    this(0.0, 0.0);
  }

  public WeaponDamageAbility(double damage, double attackSpeed) {
    super("weapon-damage-ability");
    this.addEffect(new WeaponDamageEffect(damage));
    this.addEffect(new AttackSpeedEffect(attackSpeed));
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
    return Component.text("");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

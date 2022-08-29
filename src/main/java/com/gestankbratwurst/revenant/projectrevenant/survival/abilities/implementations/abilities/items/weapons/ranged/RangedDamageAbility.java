package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.ranged;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.AttackSpeedEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.WeaponDamageEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.ranged.RangedDamageEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class RangedDamageAbility extends Ability {

  public RangedDamageAbility() {
    this(0.0, 0.0, 1);
  }

  //ToDo Entscheiden ob Fernkampfwaffen Nahkampfschaden machen und wie Fernkampfwaffen abgefeuert werden
  public RangedDamageAbility(double rangedDamage, double meleeDamage, double meleeAttackSpeed) {
    super("ranged-damage-ability");
    this.addEffect(new RangedDamageEffect(rangedDamage));
    this.addEffect(new WeaponDamageEffect(meleeDamage));
    this.addEffect(new AttackSpeedEffect(meleeAttackSpeed));
  }

  @Override
  public boolean shouldDisplayInTab() {
    return false;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return false;
  }

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

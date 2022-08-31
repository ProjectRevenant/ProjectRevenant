package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.melee;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.AttackSpeedEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.WeaponDamageEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee.WeaponKnockbackEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class WeaponDamageAbility extends Ability {

  private final double damage;
  private final double attackSpeed;
  private final double knockback;

  public WeaponDamageAbility() {
    this(0.0, 0.0, 0.0);
  }

  public WeaponDamageAbility(double damage, double attackSpeed, double knockback) {
    this.damage = damage;
    this.attackSpeed = attackSpeed;
    this.knockback = knockback;
    this.addEffect(new WeaponDamageEffect(damage));
    this.addEffect(new AttackSpeedEffect(attackSpeed));
    this.addEffect(new WeaponKnockbackEffect(knockback));
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
    return Component.text("§9Nahkampfwaffe");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text(String.format("§e%.1f §7Schaden", damage)),
            Component.text(String.format("§e%.1f §7Angriffsgeschwindigkeit", attackSpeed)),
            Component.text(String.format("§e%.1f §7Knockback", knockback))
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}

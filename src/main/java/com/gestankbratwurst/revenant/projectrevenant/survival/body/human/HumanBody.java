package com.gestankbratwurst.revenant.projectrevenant.survival.body.human;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.NaturalRegenerationAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.hunger.HungerDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.overheating.OverheatingDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.thirst.ThirstDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.undercooling.UndercoolingDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.ArmBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.LegBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.RibsBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Skeleton;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.SkullBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment.WorldEnvironmentFetcher;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HumanBody extends Body {

  @Getter
  private final Skeleton skeleton;

  public HumanBody() {
    super();

    this.skeleton = new Skeleton();
    skeleton.addBone(new ArmBone(ArmBone.RIGHT));
    skeleton.addBone(new ArmBone(ArmBone.LEFT));
    skeleton.addBone(new LegBone(LegBone.RIGHT));
    skeleton.addBone(new LegBone(LegBone.LEFT));
    skeleton.addBone(new RibsBone());
    skeleton.addBone(new SkullBone());

    BodyAttribute health = this.getAttribute(BodyAttribute.HEALTH);
    health.setMaxValue(100.0);
    health.setMinValue(0.0);
    health.setCurrentValue(100.0);

    // Water in liter
    BodyAttribute water = this.getAttribute(BodyAttribute.WATER);
    water.setMaxValue(10.0);
    water.setMinValue(0.0);
    water.setCurrentValue(10.0);

    // Nutrition on kilo calories
    BodyAttribute nutrition = this.getAttribute(BodyAttribute.NUTRITION);
    nutrition.setMaxValue(2000.0);
    nutrition.setMinValue(0.0);
    nutrition.setCurrentValue(1000.0);

    // Weight in kilogram
    BodyAttribute weight = this.getAttribute(BodyAttribute.WEIGHT);
    weight.setMaxValue(20.0);
    weight.setMinValue(0.0);
    weight.setCurrentValue(0.0);

    BodyAttribute healthShift = this.getAttribute(BodyAttribute.HEALTH_SHIFT);
    healthShift.setMaxValue(100.0);
    healthShift.setMinValue(-100.0);
    healthShift.setCurrentValue(0.0);

    // Base usage in L/tick [~1h from 100% to 0%]
    BodyAttribute waterShift = this.getAttribute(BodyAttribute.WATER_SHIFT);
    waterShift.setMaxValue(100.0);
    waterShift.setMinValue(-100.0);
    waterShift.setCurrentValue(-1.5E-4);

    // Base usage in kcal/tick [~2h from 100% to 0%]
    BodyAttribute nutritionShift = this.getAttribute(BodyAttribute.NUTRITION_SHIFT);
    nutritionShift.setMaxValue(100.0);
    nutritionShift.setMinValue(-100.0);
    nutritionShift.setCurrentValue(-0.014);

    // Temperature in °C
    BodyAttribute temperature = this.getAttribute(BodyAttribute.TEMPERATURE);
    temperature.setMaxValue(45.0);
    temperature.setMinValue(25.0);
    temperature.setCurrentValue(37.0);

    // Temperature shift in °C
    BodyAttribute temperatureShift = this.getAttribute(BodyAttribute.TEMPERATURE_SHIFT);
    temperatureShift.setMaxValue(1.0);
    temperatureShift.setMinValue(-1.0);
    temperatureShift.setCurrentValue(0.0);

    // Heat resist in °C
    BodyAttribute heatResist = this.getAttribute(BodyAttribute.HEAT_RESISTANCE);
    heatResist.setMaxValue(30.0);
    heatResist.setMinValue(0.0);
    heatResist.setCurrentValue(0.0);

    // Cold resist in °C
    BodyAttribute coldResist = this.getAttribute(BodyAttribute.COLD_RESISTANCE);
    coldResist.setMaxValue(30.0);
    coldResist.setMinValue(0.0);
    coldResist.setCurrentValue(0.0);

    // Speed in km/h
    BodyAttribute speed = this.getAttribute(BodyAttribute.SPEED);
    speed.setMaxValue(40.0);
    speed.setMinValue(0.0);
    speed.setCurrentValue(15.0);

    // Luck value in -100 -> 100
    BodyAttribute luck = this.getAttribute(BodyAttribute.LUCK);
    luck.setMaxValue(100.0);
    luck.setMinValue(-100.0);
    luck.setCurrentValue(1.5);

    // Attacks/s
    BodyAttribute attackSpeed = this.getAttribute(BodyAttribute.ATTACK_SPEED);
    attackSpeed.setMaxValue(2.5);
    attackSpeed.setMinValue(0.0);
    attackSpeed.setCurrentValue(1.0);

    // Knockback
    BodyAttribute knockback = this.getAttribute(BodyAttribute.MELEE_KNOCKBACK);
    knockback.setMaxValue(10.0);
    knockback.setMinValue(0.0);
    knockback.setCurrentValue(1.0);

    // Armor
    BodyAttribute armor = this.getAttribute(BodyAttribute.PHYSICAL_ARMOR);
    armor.setMaxValue(2000.0);
    armor.setMinValue(0.0);
    armor.setCurrentValue(0.0);

    // Range for melee attacks
    BodyAttribute meleeRange = this.getAttribute(BodyAttribute.MELEE_RANGE);
    meleeRange.setMaxValue(10.0);
    meleeRange.setMinValue(0.25);
    meleeRange.setCurrentValue(2.0);

    // Radius for the hit detection
    BodyAttribute meleeGirth = this.getAttribute(BodyAttribute.MELEE_GIRTH);
    meleeGirth.setMaxValue(5.0);
    meleeGirth.setMinValue(0.05);
    meleeGirth.setCurrentValue(1.0);

    // Melee Damage
    BodyAttribute meleeDamage = this.getAttribute(BodyAttribute.MELEE_DAMAGE);
    meleeDamage.setMaxValue(1000);
    meleeDamage.setMinValue(0.0);
    meleeDamage.setCurrentValue(1.0);

    // Critical strike chance
    BodyAttribute critChance = this.getAttribute(BodyAttribute.CRITICAL_STRIKE_CHANCE);
    critChance.setMaxValue(100.0);
    critChance.setMinValue(0.0);
    critChance.setCurrentValue(0.0);

    // Critical strike damage
    BodyAttribute critDamage = this.getAttribute(BodyAttribute.CRITICAL_STRIKE_DAMAGE);
    critDamage.setMaxValue(1000.0);
    critDamage.setMinValue(10.0);
    critDamage.setCurrentValue(50.0);

    // Noise
    BodyAttribute noise = this.getAttribute(BodyAttribute.NOISE);
    noise.setMaxValue(100);
    noise.setMinValue(0);
    noise.setCurrentValue(25);
  }

  @Override
  public void gsonPostProcess() {
    HumanBody defaultBody = new HumanBody();
    for (String identifier : BodyAttribute.getValues()) {
      if (!this.attributeMap.containsKey(identifier)) {
        this.attributeMap.put(identifier, defaultBody.getAttribute(identifier));
      }
    }
  }

  @Override
  public void recalculateWeight() {
    super.recalculateWeight();
    Player player = Bukkit.getPlayer(this.getEntityId());
    if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
      userTablist.updateBody();
    }
  }

  private void hungerAndThirstCheckup(Player player) {
    BodyAttribute nutrition = getAttribute(BodyAttribute.NUTRITION);
    BodyAttribute water = getAttribute(BodyAttribute.WATER);

    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    if (nutrition.getCurrentValue() <= 0.01) {
      if (!revenantPlayer.hasAbility(HungerDebuff.class)) {
        revenantPlayer.addAbility(new HungerDebuff());
      }
    } else if (revenantPlayer.hasAbility(HungerDebuff.class)) {
      revenantPlayer.removeAbility(HungerDebuff.class);
    }

    if (water.getCurrentValue() <= 0.001) {
      if (!revenantPlayer.hasAbility(ThirstDebuff.class)) {
        revenantPlayer.addAbility(new ThirstDebuff());
      }
    } else if (revenantPlayer.hasAbility(ThirstDebuff.class)) {
      revenantPlayer.removeAbility(ThirstDebuff.class);
    }

    if (water.getCurrentValue() >= water.getMaxValue() * 0.75 && nutrition.getCurrentValue() >= nutrition.getMaxValue() * 0.75) {
      BodyAttribute health = getAttribute(BodyAttribute.HEALTH);
      if (health.getCurrentValue() < health.getMaxValue() * 0.6) {
        revenantPlayer.addAbility(new NaturalRegenerationAbility());
      } else {
        revenantPlayer.removeAbility(NaturalRegenerationAbility.class);
      }
    } else {
      revenantPlayer.removeAbility(NaturalRegenerationAbility.class);
    }
  }

  private void temperatureCheckup(Player player) {
    double temp = WorldEnvironmentFetcher.getTemperatureAt(player.getLocation(), true);
    BodyAttribute tempAttribute = getAttribute(BodyAttribute.TEMPERATURE);
    double bodyTemp = tempAttribute.getCurrentValueModified();
    double delta = (bodyTemp - 17) - temp;
    double threshold = 3.35;
    double scalar = 1.0;
    if (delta < 0) {
      BodyAttribute coldResist = getAttribute(BodyAttribute.COLD_RESISTANCE);
      double modified = coldResist.getCurrentValueModified();
      threshold += modified;
      scalar -= 1.0 / coldResist.getMaxValue() * modified;
    } else {
      BodyAttribute heatResist = getAttribute(BodyAttribute.HEAT_RESISTANCE);
      double modified = heatResist.getCurrentValueModified();
      threshold += modified;
      scalar -= 1.0 / heatResist.getMaxValue() * modified;
    }
    if (Math.abs(delta) < threshold) {
      delta = 0;
    }
    double value = 0;
    if (delta > 0) {
      value = ((delta * delta * 0.0016) / 1200) * -1;
    } else if (delta < 0) {
      value = ((delta * delta) * 0.002) / 1200;
    }
    double resultingValue = value * scalar;
    if (Math.abs(resultingValue) < 0.00001 && Math.abs(bodyTemp - 37) > 0.05) {
      resultingValue = bodyTemp < 37 ? 0.00015 : -0.00015;
    }
    BodyAttribute tempShiftAttribute = getAttribute(BodyAttribute.TEMPERATURE_SHIFT);
    tempShiftAttribute.setCurrentValue(resultingValue);
    tempAttribute.applyToCurrentValue(current -> current + tempShiftAttribute.getCurrentValueModified() * 20);
    double afterTemp = tempAttribute.getCurrentValueModified();
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    if (afterTemp <= 33) {
      if (!revenantPlayer.hasAbility(UndercoolingDebuff.class)) {
        revenantPlayer.addAbility(new UndercoolingDebuff());
      }
    } else if (revenantPlayer.hasAbility(UndercoolingDebuff.class)) {
      revenantPlayer.removeAbility(UndercoolingDebuff.class);
    }

    if (afterTemp >= 40) {
      if (!revenantPlayer.hasAbility(OverheatingDebuff.class)) {
        revenantPlayer.addAbility(new OverheatingDebuff());
      }
    } else if (revenantPlayer.hasAbility(OverheatingDebuff.class)) {
      revenantPlayer.removeAbility(OverheatingDebuff.class);
    }
  }

  @Override
  protected void tickSeconds() {
    super.tickSeconds();
    Player player = Bukkit.getPlayer(this.getEntityId());
    if (player == null) {
      return;
    }
    temperatureCheckup(player);
    hungerAndThirstCheckup(player);
    if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
      userTablist.updateBody();
    }
  }
}

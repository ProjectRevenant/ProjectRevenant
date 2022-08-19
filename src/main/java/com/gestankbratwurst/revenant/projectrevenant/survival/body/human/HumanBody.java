package com.gestankbratwurst.revenant.projectrevenant.survival.body.human;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
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

  @Override
  public void tick() {
    Player player = Bukkit.getPlayer(this.getEntityId());
    if (player == null) {
      return;
    }
    temperatureCheckup(player);
    hungerAndThirstCheckup(player);
    super.tick();
  }

  private void hungerAndThirstCheckup(Player player) {
    BodyAttribute nutrition = getAttribute(BodyAttribute.NUTRITION);
    BodyAttribute water = getAttribute(BodyAttribute.WATER);

    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    if(nutrition.getCurrentValue() <= 0.01) {
      if(!revenantPlayer.hasAbility(RevenantAbility.HUNGER_DEBUFF)) {
        revenantPlayer.addAbility(new HungerDebuff());
      }
    } else if(revenantPlayer.hasAbility(RevenantAbility.HUNGER_DEBUFF)) {
      revenantPlayer.removeAbility(RevenantAbility.HUNGER_DEBUFF);
    }

    if(water.getCurrentValue() <= 0.001) {
      if(!revenantPlayer.hasAbility(RevenantAbility.THIRST_DEBUFF)) {
        revenantPlayer.addAbility(new ThirstDebuff());
      }
    } else if(revenantPlayer.hasAbility(RevenantAbility.THIRST_DEBUFF)) {
      revenantPlayer.removeAbility(RevenantAbility.THIRST_DEBUFF);
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
    if(Math.abs(resultingValue) < 0.00001 && Math.abs(bodyTemp - 37) > 0.05) {
      resultingValue = bodyTemp < 37 ? 0.00015 : -0.00015;
    }
    BodyAttribute tempShiftAttribute = getAttribute(BodyAttribute.TEMPERATURE_SHIFT);
    tempShiftAttribute.setCurrentValue(resultingValue);
    tempAttribute.applyToCurrentValue(current -> current + tempShiftAttribute.getCurrentValueModified());
    double afterTemp = tempAttribute.getCurrentValueModified();
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    if(afterTemp <= 34.5) {
      if(!revenantPlayer.hasAbility(RevenantAbility.UNDERCOOLING_DEBUFF)) {
        revenantPlayer.addAbility(new UndercoolingDebuff());
      }
    } else if (revenantPlayer.hasAbility(RevenantAbility.UNDERCOOLING_DEBUFF)) {
      revenantPlayer.removeAbility(RevenantAbility.UNDERCOOLING_DEBUFF);
    }

    if(afterTemp >= 39.0) {
      if(!revenantPlayer.hasAbility(RevenantAbility.OVERHEATING_DEBUFF)) {
        revenantPlayer.addAbility(new OverheatingDebuff());
      }
    } else if (revenantPlayer.hasAbility(RevenantAbility.OVERHEATING_DEBUFF)) {
      revenantPlayer.removeAbility(RevenantAbility.OVERHEATING_DEBUFF);
    }
  }

  @Override
  protected void tickSeconds() {
    super.tickSeconds();
    Player player = Bukkit.getPlayer(this.getEntityId());
    if (player == null) {
      return;
    }
    if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
      userTablist.updateBody();
    }
  }
}

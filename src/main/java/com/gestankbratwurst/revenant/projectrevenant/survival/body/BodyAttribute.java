package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public class BodyAttribute {

  public static String HEALTH = "health";
  public static String WEIGHT = "weight";
  public static String NUTRITION = "nutrition";
  public static String WATER = "water";
  public static String TEMPERATURE = "temperature";
  public static String HEALTH_SHIFT = "health_shift";
  public static String WEIGHT_SHIFT = "weight_shift";
  public static String NUTRITION_SHIFT = "nutrition_shift";
  public static String WATER_SHIFT = "water_shift";
  public static String TEMPERATURE_SHIFT = "temperature_shift";
  public static String HEAT_RESISTANCE = "heat_resistance";
  public static String COLD_RESISTANCE = "cold_resistance";
  public static String PHYSICAL_ARMOR = "physical_armor";
  public static String MELEE_DAMAGE = "melee_damage";
  public static String RANGED_DAMAGE = "ranged_damage";
  public static String SPEED = "speed";
  public static String LUCK = "luck";

  @Getter
  private static final String[] values = {
          HEALTH,
          WEIGHT,
          NUTRITION,
          WATER,
          TEMPERATURE,
          HEALTH_SHIFT,
          WEIGHT_SHIFT,
          NUTRITION_SHIFT,
          WATER_SHIFT,
          TEMPERATURE_SHIFT,
          HEAT_RESISTANCE,
          COLD_RESISTANCE,
          PHYSICAL_ARMOR,
          MELEE_DAMAGE,
          RANGED_DAMAGE,
          SPEED,
          LUCK
  };

  @Getter
  private final String identifier;
  @Getter
  @Setter
  private double maxValue;
  @Getter
  @Setter
  private double minValue;
  @Getter
  private double currentValue;
  private final Map<String, BodyAttributeModifier> modifierMap;

  public BodyAttribute(String identifier, double baseValue) {
    this.identifier = identifier;
    this.maxValue = baseValue;
    modifierMap = new HashMap<>();
  }

  public BodyAttribute() {
    this("", 0.0);
  }

  public void applyToMaxValue(DoubleUnaryOperator operator) {
    setMaxValue(operator.applyAsDouble(maxValue));
  }

  public void applyToMinValue(DoubleUnaryOperator operator) {
    setMinValue(operator.applyAsDouble(minValue));
  }

  public void applyToCurrentValue(DoubleUnaryOperator operator) {
    setCurrentValue(operator.applyAsDouble(currentValue));
  }

  public void applyToCurrentValueUnsafe(DoubleUnaryOperator operator) {
    currentValue = operator.applyAsDouble(currentValue);
  }

  public void setCurrentValue(double amount) {
    currentValue = Math.max(minValue, Math.min(getMaxValueModified(), amount));
  }

  public void setCurrentValueUnsafe(double amount) {
    currentValue = amount;
  }

  public boolean hasModifier(String identifier) {
    return modifierMap.containsKey(identifier);
  }

  public boolean hasModifier(BodyAttributeModifier modifier) {
    return hasModifier(modifier.getIdentifier());
  }

  public void addModifier(BodyAttributeModifier modifier) {
    if (hasModifier(modifier)) {
      BodyAttributeModifier other = modifierMap.get(modifier.getIdentifier());
      if (modifier.compareTo(other) > 0) {
        modifierMap.put(modifier.getIdentifier(), modifier);
      }
    } else {
      modifierMap.put(modifier.getIdentifier(), modifier);
    }
  }

  public double getMaxValueModified() {
    double value = maxValue;
    for (BodyAttributeModifier modifier : modifierMap.values()) {
      value = modifier.applyAsDouble(value);
    }
    return value;
  }

  public double getCurrentValueModified() {
    double value = currentValue;
    for (BodyAttributeModifier modifier : modifierMap.values()) {
      value = modifier.applyAsDouble(value);
    }
    return value;
  }

  public void clearModifiers() {
    modifierMap.clear();
  }
}

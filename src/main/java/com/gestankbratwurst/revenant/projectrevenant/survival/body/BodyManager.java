package com.gestankbratwurst.revenant.projectrevenant.survival.body;

import com.gestankbratwurst.core.mmcore.data.json.GsonProvider;
import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment.WorldEnvironmentFetcher;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BodyManager {

  private final Map<UUID, Body> loadedBodyMap;
  private final NamespacedKey bodyDataKey;

  public BodyManager() {
    this.loadedBodyMap = new HashMap<>();
    bodyDataKey = NamespaceFactory.provide("body-data");
  }

  public void addBodyTo(LivingEntity entity, Body body) {
    body.setEntityId(entity.getUniqueId());
    entity.getPersistentDataContainer().set(bodyDataKey, PersistentDataType.STRING, GsonProvider.INSTANCE.toJson(body));
  }

  public void loadBody(LivingEntity entity) {
    PersistentDataContainer container = entity.getPersistentDataContainer();
    String data = container.get(bodyDataKey, PersistentDataType.STRING);
    Body body = data == null ? new DummyBody() : GsonProvider.INSTANCE.fromJson(data, Body.class);
    body.setEntityId(entity.getUniqueId());
    loadedBodyMap.put(entity.getUniqueId(), body);
  }

  public boolean hasBody(LivingEntity entity) {
    return entity.getPersistentDataContainer().has(bodyDataKey);
  }

  public Body getBody(LivingEntity entity) {
    return loadedBodyMap.get(entity.getUniqueId());
  }

  public void unloadBody(LivingEntity entity) {
    Optional.ofNullable(loadedBodyMap.remove(entity.getUniqueId())).ifPresent(body -> addBodyTo(entity, body));
  }

  public void removeBody(LivingEntity entity) {
    loadedBodyMap.remove(entity.getUniqueId());
  }

  protected void tickLiveBodies() {
    new ArrayList<>(this.loadedBodyMap.values()).forEach(Body::tick);
  }

  public String getInfoIcons(Player player) {
    HumanBody body = (HumanBody) getBody(player);

    BodyAttribute health = body.getAttribute(BodyAttribute.HEALTH);
    BodyAttribute water = body.getAttribute(BodyAttribute.WATER);
    BodyAttribute nutrition = body.getAttribute(BodyAttribute.NUTRITION);
    BodyAttribute weight = body.getAttribute(BodyAttribute.WEIGHT);
    BodyAttribute temperature = body.getAttribute(BodyAttribute.TEMPERATURE);

    BodyAttribute healthShift = body.getAttribute(BodyAttribute.HEALTH_SHIFT);
    BodyAttribute weightShift = body.getAttribute(BodyAttribute.WEIGHT_SHIFT);
    BodyAttribute waterShift = body.getAttribute(BodyAttribute.WATER_SHIFT);
    BodyAttribute nutritionShift = body.getAttribute(BodyAttribute.NUTRITION_SHIFT);
    BodyAttribute tempShift = body.getAttribute(BodyAttribute.TEMPERATURE_SHIFT);

    return "" +
            BodyAttributeIcon.of(BodyAttribute.HEALTH, 1.0 / health.getMaxValueModified() * health.getCurrentValue()).getChar() +
            BodyAttributeIcon.of(BodyAttribute.HEALTH_SHIFT, 1.0 / (health.getMaxValueModified() / 10000.0) * healthShift.getCurrentValueModified()).getChar() + " " +
            BodyAttributeIcon.of(BodyAttribute.NUTRITION, 1.0 / nutrition.getMaxValueModified() * nutrition.getCurrentValue()).getChar() +
            BodyAttributeIcon.of(BodyAttribute.NUTRITION_SHIFT, 1.0 / (nutrition.getMaxValueModified() / 21000.0) * nutritionShift.getCurrentValueModified()).getChar() + " " +
            BodyAttributeIcon.of(BodyAttribute.WATER, 1.0 / water.getMaxValueModified() * water.getCurrentValue()).getChar() +
            BodyAttributeIcon.of(BodyAttribute.WATER_SHIFT, 1.0 / (water.getMaxValueModified() / 11500.0) * waterShift.getCurrentValueModified()).getChar() + " " +
            BodyAttributeIcon.of(BodyAttribute.WEIGHT, 1.0 / weight.getMaxValueModified() * weight.getCurrentValue()).getChar() +
            BodyAttributeIcon.of(BodyAttribute.WEIGHT_SHIFT, 1.0 / (weight.getMaxValueModified() / 10000.0) * weightShift.getCurrentValueModified()).getChar() + " " +
            WorldEnvironmentFetcher.getThermometer(temperature.getCurrentValue(), 22.5, 45.0).getChar() +
            BodyAttributeIcon.of(BodyAttribute.TEMPERATURE_SHIFT, 1.0 / 3.0 * (tempShift.getCurrentValueModified() * 1000)).getChar();
  }


}

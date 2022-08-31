package com.gestankbratwurst.revenant.projectrevenant.util.gson;

import com.google.gson.*;
import io.leangen.geantyref.TypeToken;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Type;
import java.util.Map;

public class PotionEffectSerializer implements JsonSerializer<PotionEffect>, JsonDeserializer<PotionEffect> {
  @Override
  public PotionEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();

    // Get stuff
    int amplifier = jsonObject.get("amp").getAsInt();
    int duration = jsonObject.get("dur").getAsInt();
    PotionEffectType type = PotionEffectType.getByName(jsonObject.get("type").getAsString());
    boolean ambient = jsonObject.get("ambient").getAsBoolean();
    boolean particles = jsonObject.get("particles").getAsBoolean();
    boolean icon = jsonObject.get("icon").getAsBoolean();

    if(type == null){
      throw new JsonParseException("Could not get PotionEffectType by name");
    }

    return new PotionEffect(type, duration, amplifier, ambient, particles, icon);
  }

  @Override
  public JsonElement serialize(PotionEffect src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("amp", src.getAmplifier());
    jsonObject.addProperty("dur", src.getDuration());
    jsonObject.addProperty("type", src.getType().getName());
    jsonObject.addProperty("ambient", src.isAmbient());
    jsonObject.addProperty("particles", src.hasParticles());
    jsonObject.addProperty("icon", src.hasIcon());

    return jsonObject;
  }
}

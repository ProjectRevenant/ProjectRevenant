package com.gestankbratwurst.revenant.projectrevenant.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.leangen.geantyref.TypeToken;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Type;
import java.util.Map;

public class PotionEffectSerializer implements JsonSerializer<PotionEffect>, JsonDeserializer<PotionEffect> {
  @Override
  public PotionEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return new PotionEffect(context.deserialize(json, new TypeToken<Map<String, Object>>() {
    }.getType()));
  }

  @Override
  public JsonElement serialize(PotionEffect src, Type typeOfSrc, JsonSerializationContext context) {
    return context.serialize(src.serialize());
  }
}

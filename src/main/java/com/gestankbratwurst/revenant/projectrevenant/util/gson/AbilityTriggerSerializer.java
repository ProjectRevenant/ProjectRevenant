package com.gestankbratwurst.revenant.projectrevenant.util.gson;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AbilityTriggerSerializer implements JsonSerializer<AbilityTrigger<?>>, JsonDeserializer<AbilityTrigger<?>> {
  @Override
  public AbilityTrigger<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return AbilityTrigger.fromIdentifier(json.getAsJsonPrimitive().getAsString());
  }

  @Override
  public JsonElement serialize(AbilityTrigger<?> src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.getIdentifier());
  }
}

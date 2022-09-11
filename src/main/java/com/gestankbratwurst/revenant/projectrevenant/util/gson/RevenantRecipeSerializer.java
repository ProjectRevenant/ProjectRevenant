package com.gestankbratwurst.revenant.projectrevenant.util.gson;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.UUID;

public class RevenantRecipeSerializer implements JsonSerializer<RevenantRecipe>, JsonDeserializer<RevenantRecipe> {
  @Override
  public RevenantRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return ProjectRevenant.getRevenantRecipeManager().getRecipe(UUID.fromString(json.getAsJsonPrimitive().getAsString()));
  }

  @Override
  public JsonElement serialize(RevenantRecipe src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.getId().toString());
  }
}

package com.gestankbratwurst.revenant.projectrevenant.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Type;

public class BlockDataSerializer implements JsonSerializer<BlockData>, JsonDeserializer<BlockData> {
  @Override
  public BlockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return Bukkit.createBlockData(json.getAsJsonPrimitive().getAsString());
  }

  @Override
  public JsonElement serialize(BlockData src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.getAsString());
  }
}

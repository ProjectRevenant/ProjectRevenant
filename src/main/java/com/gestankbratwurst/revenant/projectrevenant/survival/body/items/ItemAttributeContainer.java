package com.gestankbratwurst.revenant.projectrevenant.survival.body.items;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ItemAttributeContainer implements Iterable<BodyAttributeModifier> {

  private static final ItemAttributeContainer empty = new ItemAttributeContainer(Map.of());

  public static ItemAttributeContainer empty() {
    return empty;
  }

  private final Map<String, BodyAttributeModifier> modifierMap;

  public ItemAttributeContainer() {
    this(new HashMap<>());
  }

  private ItemAttributeContainer(Map<String, BodyAttributeModifier> map) {
    this.modifierMap = map;
  }

  public boolean hasModifier(String identifier) {
    return modifierMap.containsKey(identifier);
  }

  public boolean hasModifier(BodyAttributeModifier modifier) {
    return modifierMap.containsKey(modifier.getIdentifier());
  }

  public void add(BodyAttributeModifier modifier) {
    modifierMap.put(modifier.getIdentifier(), modifier);
  }

  public BodyAttributeModifier get(String identifier) {
    return modifierMap.get(identifier);
  }

  public Collection<BodyAttributeModifier> values() {
    return modifierMap.values();
  }

  @NotNull
  @Override
  public Iterator<BodyAttributeModifier> iterator() {
    return values().iterator();
  }
}

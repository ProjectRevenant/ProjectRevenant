package com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class WorldDomain<T> implements Iterable<T> {

  private final Map<Long, T> worldDomainMap = new HashMap<>();

  public T getInChunk(Long chunk) {
    return worldDomainMap.get(chunk);
  }

  public void addInChunk(Long chunk, T inChunk) {
    worldDomainMap.put(chunk, inChunk);
  }

  public void removeInChunk(Long chunk) {
    worldDomainMap.remove(chunk);
  }

  public Collection<T> getValues() {
    return worldDomainMap.values();
  }

  public boolean isEmpty() {
    return worldDomainMap.isEmpty();
  }

  public Set<Long> getKeys() {
    return worldDomainMap.keySet();
  }

  public T getOrCreate(Long chunk, Function<Long, T> creator) {
    return worldDomainMap.computeIfAbsent(chunk, creator);
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return worldDomainMap.values().iterator();
  }
}

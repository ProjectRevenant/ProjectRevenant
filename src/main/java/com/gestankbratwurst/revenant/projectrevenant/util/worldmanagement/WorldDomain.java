package com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WorldDomain<T> implements Iterable<T> {

  private final Map<Long, T> worldDomainMap = new HashMap<>();

  public T getInChunk(Long chunk) {
    return worldDomainMap.get(chunk);
  }

  public void addInChunk(Long chunk, T inChunk) {
    worldDomainMap.put(chunk, inChunk);
  }

  public void removeInChunk(Long chunk){
    worldDomainMap.remove(chunk);
  }

  public boolean isEmpty(){
    return worldDomainMap.isEmpty();
  }

  public Set<Long> getKeys() {
    return worldDomainMap.keySet();
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return worldDomainMap.values().iterator();
  }
}

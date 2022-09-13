package com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChunkDomain<T> implements Iterable<T> {

  private final Map<Integer, T> chunkDomainMap = new HashMap<>();

  public T getAtLocation(Integer locationInChunk) {
    return chunkDomainMap.get(locationInChunk);
  }

  public void addAtLocation(Integer locationInChunk, T atLocation) {
    chunkDomainMap.put(locationInChunk, atLocation);
  }

  public T removeAtLocation(Integer locationInChunk) {
    return chunkDomainMap.remove(locationInChunk);
  }

  public boolean isEmpty() {
    return chunkDomainMap.isEmpty();
  }

  public Set<Integer> getKeys() {
    return chunkDomainMap.keySet();
  }

  public Collection<T> getValues() {
    return chunkDomainMap.values();
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return chunkDomainMap.values().iterator();
  }
}

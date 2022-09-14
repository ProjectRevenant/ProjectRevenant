package com.gestankbratwurst.revenant.projectrevenant.data.player;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.data.mongodb.annotationframework.MappedMongoStorage;
import org.jetbrains.annotations.NotNull;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RevenantPlayerManager implements Flushable, Iterable<RevenantPlayer> {

  private final Map<UUID, RevenantPlayer> loadedPlayerMap = new ConcurrentHashMap<>();
  private final MappedMongoStorage<UUID, RevenantPlayer> mongoStorage = MMCore.getMongoStorage().mapped("RevenantPlayer", RevenantPlayer.class);

  public RevenantPlayer getOnline(UUID playerId) {
    return loadedPlayerMap.get(playerId);
  }

  public void loadData(UUID playerId) {
    loadedPlayerMap.put(playerId, Optional.ofNullable(mongoStorage.load(playerId)).orElse(new RevenantPlayer(playerId)));
  }

  public void unloadData(UUID playerId) {
    Optional.ofNullable(loadedPlayerMap.remove(playerId)).ifPresent(mongoStorage::persist);
  }

  private void saveData(UUID playerId) {
    Optional.ofNullable(loadedPlayerMap.get(playerId)).ifPresent(mongoStorage::persist);
  }

  @Override
  public void flush() {
    new ArrayList<>(loadedPlayerMap.keySet()).forEach(this::saveData);
  }

  @NotNull
  @Override
  public Iterator<RevenantPlayer> iterator() {
    return loadedPlayerMap.values().iterator();
  }
}

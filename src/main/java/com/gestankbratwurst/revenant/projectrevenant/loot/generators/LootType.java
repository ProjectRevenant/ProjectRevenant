package com.gestankbratwurst.revenant.projectrevenant.loot.generators;

import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.implementations.DummyLootGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum LootType {

  DUMMY_LOOT(new DummyLootGenerator());

  private final Function<Player, Loot> generator;

}

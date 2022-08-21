package com.gestankbratwurst.revenant.projectrevenant.loot.generators;

import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import org.bukkit.entity.Player;

import java.util.function.Function;

public interface LootGenerator extends Function<Player, Loot> {

}
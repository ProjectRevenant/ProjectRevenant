package com.gestankbratwurst.revenant.projectrevenant.loot.generators;

import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.implementations.DummyLootGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum LootType {

  DUMMY_LOOT(new DummyLootGenerator(), Duration.ofSeconds(5).toMillis(), 10, MiniMessage.builder().tags(TagResolver.builder()
                  .resolver(StandardTags.color())
                  .resolver(StandardTags.decorations())
                  .resolver(StandardTags.gradient())
                  .build())
          .build().deserialize("<gradient:blue:dark_red>Dummy Loot</gradient>"));

  private final Function<Player, Loot> generator;
  private final long respawnTimeMillis;
  private final int score;
  private final Component displayName;

}

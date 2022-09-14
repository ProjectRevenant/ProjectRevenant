package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.function.Supplier;

@Data
@AllArgsConstructor
public class Perk<T extends PerkAbility> {

  private final Class<T> abilityClass;
  private final Supplier<T> perkAbilitySupplier;
  private final Component displayName;
  private final List<Component> description;

}

package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.revenant.projectrevenant.crafting.implementation.DummyRecipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BaseRecipe {

  DUMMY(new DummyRecipe(), false);

  @Getter
  private final RevenantRecipe revenantRecipe;
  @Getter
  private final boolean startingRecipe;

}

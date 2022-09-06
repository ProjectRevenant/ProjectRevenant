package com.gestankbratwurst.revenant.projectrevenant.crafting;

import com.gestankbratwurst.revenant.projectrevenant.crafting.implementation.DummyRecipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BaseRecipe {

  DUMMY(new DummyRecipe());

  @Getter
  private final RevenantRecipe revenantRecipe;

}

package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecipeType {

  COOKED("Gekochtes"),
  BAKED("Gebacken"),
  FORGED("Geschmiedetes"),
  BREWED("Gebrautes"),
  CRAFTED("Gebautes"),
  SMELTED("Verhüttet");

  private final String displayName;

}

package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluator;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityHandle;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public class PersistentDataContainerAbilityEvaluator extends AbilityEvaluator<PersistentDataContainer> {

  public PersistentDataContainerAbilityEvaluator() {
    super(PersistentDataContainer.class);
  }

  @Override
  public List<Ability> evaluate(PersistentDataContainer target) {
    return AbilityHandle.getFrom(target);
  }
}

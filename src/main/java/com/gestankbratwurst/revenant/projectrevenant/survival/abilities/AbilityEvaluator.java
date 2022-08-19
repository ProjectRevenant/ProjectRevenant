package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbilityEvaluator<T> {

  @Getter
  private final Class<T> evaluationTargetClass;

  public abstract List<Ability> evaluate(T target);

}

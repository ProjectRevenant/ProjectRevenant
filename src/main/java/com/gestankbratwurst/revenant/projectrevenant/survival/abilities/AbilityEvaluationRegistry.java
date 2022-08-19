package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class AbilityEvaluationRegistry {

  private static final Map<Class<?>, AbilityEvaluator<?>> evaluatorMap = new HashMap<>();

  public static <T> void register(AbilityEvaluator<T> evaluator) {
    evaluatorMap.put(evaluator.getEvaluationTargetClass(), evaluator);
  }

  public static <T> Optional<AbilityEvaluator<T>> getTyped(Class<T> targetClass) {
    return Optional.ofNullable((AbilityEvaluator<T>) evaluatorMap.get(targetClass));
  }

  public static Optional<AbilityEvaluator<?>> get(Class<?> targetClass) {
    return Optional.ofNullable(evaluatorMap.get(targetClass));
  }

}

package com.gestankbratwurst.revenant.projectrevenant.metaprogression.levelsystem;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import redempt.crunch.CompiledExpression;
import redempt.crunch.Crunch;
import redempt.crunch.functional.EvaluationEnvironment;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class LevelEvaluator {

  private static final EvaluationEnvironment levelEnvironment;
  private static final CompiledExpression levelExpression;
  private static final RangeMap<Double, Integer> expToLevelMap = TreeRangeMap.create();
  private static final Map<Integer, Double> levelToExpMap = Maps.newHashMap();

  static {
    levelEnvironment = new EvaluationEnvironment();
    levelEnvironment.setVariableNames("lvl");
    levelExpression = Crunch.compileExpression("2 * lvl * lvl + 25 * lvl + 150 + 1.10775 ^ lvl", levelEnvironment);
    double fullExperience = 0;
    for(int level = 0; level < 100; level++) {
      double toNextLevel = levelExpression.evaluate(level);
      expToLevelMap.put(Range.closedOpen(fullExperience, fullExperience + toNextLevel), level);
      levelToExpMap.put(level, fullExperience);
      fullExperience += toNextLevel;
    }
  }

  public static int getLevelForExp(double experience) {
    return Optional.ofNullable(expToLevelMap.get(Math.max(0, experience))).orElse(100);
  }

  public static double getExpStart(int level) {
    return levelToExpMap.getOrDefault(Math.max(0, level), 1E9D);
  }

  public static double getExpEnd(int level) {
    return levelToExpMap.getOrDefault(Math.max(0, level + 1), 1E9D) - 1.0D;
  }

}

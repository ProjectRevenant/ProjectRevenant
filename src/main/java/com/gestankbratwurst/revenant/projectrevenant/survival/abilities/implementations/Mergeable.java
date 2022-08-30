package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations;

@FunctionalInterface
public interface Mergeable<T> {

  void merge(T other);

}

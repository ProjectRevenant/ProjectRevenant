package com.gestankbratwurst.revenant.projectrevenant.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class DistributedTask<T> implements Runnable {
  private final List<Deque<T>> distribution;
  private final int size;
  private int index = 0;

  public DistributedTask(int size) {
    distribution = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      distribution.add(new ArrayDeque<>());
    }
    this.size = size;
  }

  private Deque<T> getSmallest() {
    int smallestSize = 1000000000;
    Deque<T> smallest = null;
    for (Deque<T> queue : distribution) {
      int size = queue.size();
      if (size == 0) {
        return queue;
      } else if (size < smallestSize) {
        smallestSize = size;
        smallest = queue;
      }
    }
    return smallest;
  }

  public void add(T element) {
    getSmallest().add(element);
  }

  public void remove(T element) {
    for (Deque<T> queue : distribution) {
      queue.remove(element);
    }
  }

  @Override
  public void run() {
    distribution.get(index).forEach(this::onTick);
    index++;
    if (index == size) {
      index = 0;
    }
  }

  public abstract void onTick(T element);

}

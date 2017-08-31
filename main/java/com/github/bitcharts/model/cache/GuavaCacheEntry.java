package com.github.bitcharts.model.cache;

/**
 * Created by Radu on 8/31/2017.
 */
public class GuavaCacheEntry <T> implements CacheEntry {
  private T value;

  public GuavaCacheEntry(T value) {
    this.value = value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }
}

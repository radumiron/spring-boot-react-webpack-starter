package com.github.bitcharts.model.cache;

/**
 * Created by Radu on 8/23/2017.
 */
public class Cache2KCacheEntry <T> implements CacheEntry {
  private T value;

  public Cache2KCacheEntry(T value) {
    this.value = value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }
}

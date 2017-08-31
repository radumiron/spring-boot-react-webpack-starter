package com.github.bitcharts.model.cache;

/**
 * Created by Radu on 8/31/2017.
 */
public class GuavaCacheKey implements CacheKey {
  private final CacheKeyType type;

  public GuavaCacheKey(CacheKeyType type) {
    this.type = type;
  }

  @Override
  public CacheKeyType getType() {
    return type;
  }
}

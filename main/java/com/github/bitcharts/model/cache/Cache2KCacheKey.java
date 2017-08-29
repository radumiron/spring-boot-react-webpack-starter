package com.github.bitcharts.model.cache;

/**
 * Created by Radu on 8/23/2017.
 */
public final class Cache2KCacheKey implements CacheKey {

  private Cache2KCacheKeyType keyType;

  public Cache2KCacheKey(Cache2KCacheKeyType keyType) {
    this.keyType = keyType;
  }

  public Cache2KCacheKeyType getType() {
    return keyType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Cache2KCacheKey)) return false;

    Cache2KCacheKey that = (Cache2KCacheKey) o;

    return keyType == that.keyType;

  }

  @Override
  public int hashCode() {
    return keyType != null ? keyType.hashCode() : 0;
  }
}

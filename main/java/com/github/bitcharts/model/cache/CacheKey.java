package com.github.bitcharts.model.cache;

/**
 * Created by Radu on 8/23/2017.
 */
public interface CacheKey {
  enum Cache2KCacheKeyType {
    SUPPORTED_MARKETS
  }

  Cache2KCacheKeyType getType();
}

package com.github.bitcharts.model.cache;

/**
 * Created by Radu on 8/23/2017.
 */
public interface CacheKey {
  enum CacheKeyType {
    SUPPORTED_MARKETS
  }

  CacheKeyType getType();
}

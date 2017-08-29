package com.github.bitcharts.cache;

import org.springframework.stereotype.Service;

import com.github.bitcharts.model.cache.CacheEntry;
import com.github.bitcharts.model.cache.CacheKey;

/**
 * Created by Radu on 8/23/2017.
 */
@Service
public abstract class CacheService<K extends CacheKey, V extends CacheEntry> {

  public abstract void initCache();
  public abstract V getValue(K key);
  public abstract void setValue(K key, V value);
}

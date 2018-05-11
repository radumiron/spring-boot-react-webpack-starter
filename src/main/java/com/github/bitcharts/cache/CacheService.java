package com.github.bitcharts.cache;

import org.springframework.stereotype.Service;

import com.github.bitcharts.model.cache.CacheEntry;
import com.github.bitcharts.model.cache.CacheKey;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by Radu on 8/23/2017.
 */
@Service
public abstract class CacheService<K extends CacheKey, V extends CacheEntry> {

  protected XChangeTrading trading;

  public abstract void initCache();
  public abstract V getValue(K key);
  public abstract void addValue(K key, V value);
}

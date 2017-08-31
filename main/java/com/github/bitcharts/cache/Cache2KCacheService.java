package com.github.bitcharts.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.cache2k.integration.CacheLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.cache.Cache2KCacheEntry;
import com.github.bitcharts.model.cache.Cache2KCacheKey;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by Radu on 8/23/2017.
 */
@Service
public class Cache2KCacheService extends CacheService<Cache2KCacheKey, Cache2KCacheEntry> {

  private static final int TIME_TO_LIVE = 50000;

  private XChangeTrading trading;

  private Cache<Cache2KCacheKey, Cache2KCacheEntry> tradingCache;

  @Autowired
  public Cache2KCacheService(XChangeTrading trading) {
    Assert.notNull(trading, "Trading object must not be null");
    this.trading = trading;
  }

  @PostConstruct
  public void initCache() {
    tradingCache = new Cache2kBuilder<Cache2KCacheKey, Cache2KCacheEntry>() {}
        .name("tradingCache")
        .loader(new CacheLoader<Cache2KCacheKey, Cache2KCacheEntry>() {
          @Override
          public Cache2KCacheEntry load(final Cache2KCacheKey key) throws Exception {
            return loadDataFromTradingService(key);
          }
        })
        .expiryPolicy((key, value, loadTime, oldEntry) -> TIME_TO_LIVE)
        .expireAfterWrite(TIME_TO_LIVE, TimeUnit.MILLISECONDS)
        .sharpExpiry(true)
        .build();
  }

  private Cache2KCacheEntry loadDataFromTradingService(Cache2KCacheKey key) {
    switch (key.getType()) {
      case SUPPORTED_MARKETS:
        Set<Markets> marketsSet = trading.getSupportedMarkets();
        return new Cache2KCacheEntry<>(marketsSet);
      default: return null;
    }
  }

  @Override
  public Cache2KCacheEntry getValue(Cache2KCacheKey key) {
    return tradingCache.get(key);
  }

  @Override
  public void setValue(Cache2KCacheKey key, Cache2KCacheEntry value) {
    tradingCache.put(key, value);
  }
}

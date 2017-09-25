package com.github.bitcharts.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.cache.*;
import com.github.bitcharts.trading.XChangeTrading;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

/**
 * Created by Radu on 8/31/2017.
 */
@Service
public class GoogleGuavaCacheService extends CacheService<GuavaCacheKey, GuavaCacheEntry>{

  public final AtomicLong timeToLive = new AtomicLong(5000);

  private static final Logger LOG = Logger.getLogger(GoogleGuavaCacheService.class);

  private final XChangeTrading trading;

  private com.google.common.cache.LoadingCache<GuavaCacheKey, GuavaCacheEntry> cache;

  @Autowired
  public GoogleGuavaCacheService(XChangeTrading trading) {
    Assert.notNull(trading, "Trading object must not be null");
    this.trading = trading;
  }

  @PostConstruct
  public void initCache() {
    initCache(timeToLive.get(), TimeUnit.MILLISECONDS);
  }

  public void initCache(long timeToLive, TimeUnit timeUnit) {
    cache = CacheBuilder
        .newBuilder()
        .expireAfterWrite(timeToLive, timeUnit)
        .build(new CacheLoader<GuavaCacheKey, GuavaCacheEntry>() {
          @Override
          public GuavaCacheEntry load(GuavaCacheKey key) throws Exception {
            return loadDataFromTradingService(key);
          }
        });
  }

  private GuavaCacheEntry loadDataFromTradingService(GuavaCacheKey key) {
    switch (key.getType()) {
      case SUPPORTED_MARKETS:
        Set<Markets> marketsSet = trading.getSupportedMarkets();
        return new GuavaCacheEntry<>(marketsSet);
      case TICKER:
        GuavaCacheKeyForTicker actualKey = (GuavaCacheKeyForTicker) key;
        Ticker ticker = trading.getTicker(actualKey.getMarketName(), actualKey.getCurrencyPair());
        if (ticker != null) {
          return new GuavaCacheEntry(TickerFactory.getTickerShallowObject(ticker));
        }
        return new GuavaCacheEntry(null);
      default: return null;
    }
  }

  @Override
  public GuavaCacheEntry getValue(GuavaCacheKey key) {
    return cache.getUnchecked(key);
  }

  @Override
  public void addValue(GuavaCacheKey key, GuavaCacheEntry value) {

  }

}

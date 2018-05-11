package com.github.bitcharts.model.cache;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * Created by Radu on 8/31/2017.
 */
public class GuavaCacheKeyForTicker extends GuavaCacheKey {
  private final String marketName;
  private final CurrencyPair currencyPair;

  public GuavaCacheKeyForTicker(CacheKeyType type, String marketName, CurrencyPair currencyPair) {
    super(type);
    this.marketName = marketName;
    this.currencyPair = currencyPair;
  }

  public String getMarketName() {
    return marketName;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GuavaCacheKeyForTicker)) return false;

    GuavaCacheKeyForTicker that = (GuavaCacheKeyForTicker) o;

    if (marketName != null ? !marketName.equals(that.marketName) : that.marketName != null) return false;
    return currencyPair != null ? currencyPair.equals(that.currencyPair) : that.currencyPair == null;

  }

  @Override
  public int hashCode() {
    int result = marketName != null ? marketName.hashCode() : 0;
    result = 31 * result + (currencyPair != null ? currencyPair.hashCode() : 0);
    return result;
  }
}

package com.github.bitcharts.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Radu on 5/28/2017.
 */
public class ArbitrageMarket {
  private Markets market;
  private Set<ArbitrageMarket> counterMarkets = new LinkedHashSet<>();
  private TickerShallowObject tickerShallowObject = new TickerShallowObject();

  public ArbitrageMarket(Markets market) {
    this.market = market;
  }

  public Markets getMarket() {
    return market;
  }

  public void setMarket(Markets market) {
    this.market = market;
  }

  public Set<ArbitrageMarket> getCounterMarkets() {
    return counterMarkets;
  }

  public void setCounterMarkets(Set<ArbitrageMarket> counterMarkets) {
    this.counterMarkets = counterMarkets;
  }

  public TickerShallowObject getTickerShallowObject() {
    return tickerShallowObject;
  }

  public void setTickerShallowObject(TickerShallowObject tickerShallowObject) {
    this.tickerShallowObject = tickerShallowObject;
  }
}

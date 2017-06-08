package com.github.bitcharts.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Radu on 6/6/2017.
 */
public class MarketsModel {
  private Markets market;
  private TickerFullLayoutObject ticker;
  private Set<MarketsModel> marketsModels;

  public MarketsModel() {
    marketsModels = new LinkedHashSet<>();
  }

  public TickerFullLayoutObject getTicker() {
    return ticker;
  }

  public void setTicker(TickerFullLayoutObject ticker) {
    this.ticker = ticker;
  }

  public Set<MarketsModel> getMarketsModels() {
    return marketsModels;
  }

  public void setMarketsModels(Set<MarketsModel> marketsModels) {
    this.marketsModels = marketsModels;
  }

  public void addArbitrageModel(MarketsModel model) {
    marketsModels.add(model);
  }

  public Markets getMarket() {
    return market;
  }

  public void setMarket(Markets market) {
    this.market = market;
  }
}

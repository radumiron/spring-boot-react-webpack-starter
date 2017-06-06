package com.github.bitcharts.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Radu on 6/6/2017.
 */
public class ArbitrageModel {
  private TickerFullLayoutObject ticker;
  private Set<ArbitrageModel> arbitrageModels;

  public ArbitrageModel() {
    arbitrageModels = new LinkedHashSet<>();
  }

  public TickerFullLayoutObject getTicker() {
    return ticker;
  }

  public void setTicker(TickerFullLayoutObject ticker) {
    this.ticker = ticker;
  }

  public Set<ArbitrageModel> getArbitrageModels() {
    return arbitrageModels;
  }

  public void setArbitrageModels(Set<ArbitrageModel> arbitrageModels) {
    this.arbitrageModels = arbitrageModels;
  }

  public void addArbitrageModel(ArbitrageModel model) {
    arbitrageModels.add(model);
  }
}

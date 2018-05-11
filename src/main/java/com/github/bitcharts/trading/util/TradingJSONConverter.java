package com.github.bitcharts.trading.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.rest.MarketsJSON;

/**
 * Created by mironr on 11/10/2016.
 */
public class TradingJSONConverter {

  public static Collection<MarketsJSON> convertMarkets(Collection<Markets> markets) {
    List<MarketsJSON> result = markets.stream().map(MarketsJSON::new).collect(Collectors.toList());

    return result;
  }
}

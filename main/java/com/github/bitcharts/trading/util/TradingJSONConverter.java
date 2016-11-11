package com.github.bitcharts.trading.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.rest.MarketsJSON;

/**
 * Created by mironr on 11/10/2016.
 */
public class TradingJSONConverter {

  public static Collection<MarketsJSON> convertMarkets(Collection<Markets> markets) {
    List<MarketsJSON> result = new ArrayList<>();
    for (Markets market : markets) {
      result.add(new MarketsJSON(market));
    }

    return result;
  }
}

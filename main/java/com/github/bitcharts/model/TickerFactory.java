package com.github.bitcharts.model;

import org.knowm.xchange.dto.marketdata.Ticker;

/**
 * Created by Radu on 10/30/2016.
 */
public class TickerFactory {
  public static TickerShallowObject getTickerShallowObject(Ticker ticker) {
    TickerShallowObject result = new TickerShallowObject(ticker.getCurrencyPair(),
        ticker.getLast().doubleValue(), ticker.getTimestamp());

    return result;
  }

  public static TickerFullLayoutObject getTickerFullLayoutObject(Ticker ticker) {
    TickerFullLayoutObject result = new TickerFullLayoutObject(ticker.getCurrencyPair(), ticker.getLast().doubleValue(),
        ticker.getTimestamp(), ticker.getAsk().doubleValue(), -1d,
        ticker.getBid().doubleValue(), ticker.getHigh().doubleValue(), -1d, -1d, -1d,
        ticker.getLow().doubleValue(), ticker.getVolume().doubleValue(), -1d);

    return result;
  }
}

package com.github.bitcharts.trading.util;


import com.github.bitcharts.model.Markets;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Created by Radu on 8/15/2015.
 */
public class TradingUtil {

  public static String getMarketIdentifierName(Markets market, CurrencyPair currency) {
    return market.name().toLowerCase() + currency;
  }
}

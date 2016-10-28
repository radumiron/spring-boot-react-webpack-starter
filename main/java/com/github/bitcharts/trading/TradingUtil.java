package com.github.bitcharts.trading;


import com.github.bitcharts.model.Markets;
import org.knowm.xchange.currency.Currency;

/**
 * Created by Radu on 8/15/2015.
 */
public class TradingUtil {

  public static String getMarketIdentifierName(Markets market, Currency currency) {
    return market.name().toLowerCase() + currency.getCurrencyCode();
  }
}

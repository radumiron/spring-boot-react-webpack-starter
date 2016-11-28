package com.github.bitcharts.trading.util;


import java.util.*;
import java.util.stream.Collectors;

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

  public static Collection<Currency> getCryptoCurrencies() {
    return getCurrencies(true);
  }

  public static Set<Currency> getFiatCurrencies() {
    return getCurrencies(false);
  }

  private static Set<Currency> getCurrencies(boolean isCrypto) {
    Set<Currency> supportedCurrencies = Currency.getAvailableCurrencies();
    Set<java.util.Currency> javaCurrencies = java.util.Currency.getAvailableCurrencies();

    Set<Currency> result = supportedCurrencies
        .stream()
        .filter(currency -> {
          java.util.Currency convertedCurrency = null;
          try {
            convertedCurrency = java.util.Currency.getInstance(currency.getCurrencyCode());
          } catch (IllegalArgumentException e) {
            //we've found a not supported currency, probably crypto currency
            return isCrypto;
          }

          //means it's a currency supported by both XChange & Java
          return isCrypto != javaCurrencies.contains(convertedCurrency);
        })
        .sorted((o1, o2) -> o1.getCurrencyCode().compareTo(o2.getCurrencyCode()))
        .collect(Collectors.toSet());

    return result;
  }

  public static void main(String[] args) {
    System.out.println(getFiatCurrencies());
    System.out.println(getCryptoCurrencies());
  }
}

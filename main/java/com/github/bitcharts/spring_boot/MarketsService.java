package com.github.bitcharts.spring_boot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.trading.XChangeTrading;
import com.github.bitcharts.trading.util.TradingUtil;

/**
 * Created by Radu on 10/28/2016.
 */
@Service
public class MarketsService {

  private static final Logger LOG = Logger.getLogger(MarketsService.class);

  private XChangeTrading trading;

  @Autowired
  public MarketsService(final XChangeTrading trading) {
    this.trading = trading;
  }

  public Collection<CurrencyPair> supportedCurrencies(String marketName) {
    marketName = marketName.toUpperCase();
    try {
      if (EnumUtils.isValidEnum(Markets.class, marketName)) {
        return trading.getExchangeSymbols(marketName);
      } else {
        return Collections.emptyList();
      }
    } catch (Exception e) {
      LOG.error(e);
      return Collections.emptyList();
    }
  }

  public Collection<Currency> allSupportedFiatCurrencies(String currency) {
    return allSupportedCurrencies(currency, TradingUtil.getCurrencies(false));
  }

  public Collection<Currency> allSupportedCryptoCurrencies(String currency) {
    return allSupportedCurrencies(currency, TradingUtil.getCurrencies(true));
  }

  private Collection<Currency> allSupportedCurrencies(String cryptoCurrency, Set<Currency> supportedCurrencies) {
    final Set<Markets> supportedMarkets = trading.getSupportedMarkets();
    if (StringUtils.isEmpty(cryptoCurrency)) {  //return all supported currencies
      Set<Currency> allSupportedCurrencies = supportedMarkets
          .parallelStream()
          .map(market -> trading.getExchangeSymbols(market.name())
              .stream()
              .map(currencyPair -> currencyPair.counter)
              .filter(currency -> supportedCurrencies.contains(currency))
              .collect(Collectors.toSet()))
          .reduce(new LinkedHashSet<>(),
              (originalSet, currencySet) -> {
                originalSet.addAll(currencySet);
                return originalSet;
              });

      return allSupportedCurrencies;
    } else {
      Set<Currency> collect = supportedMarkets
          .parallelStream()
          .map(market -> trading.getExchangeSymbols(market.name())
              .stream()
              .filter(currencyPair -> currencyPair.base.equals(Currency.getInstance
                  (cryptoCurrency)) && supportedCurrencies.contains(currencyPair.counter))
              .map(currencyPair -> currencyPair.counter)
              .collect(Collectors.toSet()))
          .reduce(new LinkedHashSet<>(),
              (originalSet, addedSet) -> {
                originalSet.addAll(addedSet);
                return originalSet;
              }
                  );

      return collect;
    }
  }

  public Collection<CurrencyPair> filterSupportedCurrencies(Collection<CurrencyPair> supported,
                                                            Currency baseCurrency, Currency counterCurrency) {
    removeNonMatchingCurrencies(supported, baseCurrency, true);

    removeNonMatchingCurrencies(supported, counterCurrency, false);

    return new ArrayList<>(supported);
  }

  private void removeNonMatchingCurrencies(Collection<CurrencyPair> supported, Currency currency, boolean isBase) {
    if (supported == null) {
      return;
    }
    Iterator<CurrencyPair> it = supported.iterator();
    //remove all currencies which are not base
    if (currency != null) {
      while (it.hasNext()) {
        CurrencyPair currencyPair = it.next();
        if (isBase && !currency.equals(currencyPair.base)) {
          it.remove();
        } else if (!isBase && !currency.equals(currencyPair.counter)) {
          it.remove();
        }
      }
    }
  }

  public List<TickerObject> ticker(String marketName) {
    return this.ticker(marketName, null, null, false);
  }

  public List<TickerObject> ticker(String marketName, String baseCurrencyName, String counterCurrencyName) {
    return this.ticker(marketName, baseCurrencyName, counterCurrencyName, false);
  }

  public List<TickerObject> ticker(String marketName, String baseCurrencyName, String counterCurrencyName, boolean isFullTicker) {
    marketName = marketName.toUpperCase();
    try {
      List<TickerObject> result = new ArrayList<>();
      if (baseCurrencyName != null && counterCurrencyName != null) { //return the ticker for one currency
        CurrencyPair pair = new CurrencyPair(Currency.getInstance(baseCurrencyName), Currency.getInstance(counterCurrencyName));
        Ticker ticker = getTicker(marketName, pair);
        if (isFullTicker) {
          result.add(TickerFactory.getTickerFullLayoutObject(ticker));
        } else {
          result.add(TickerFactory.getTickerShallowObject(ticker));
        }
      } else if (baseCurrencyName != null) { //return the ticker for the baseCurrencyName
        Currency baseCurrency = Currency.getInstance(baseCurrencyName);
        result.addAll(getTickerForMarket(marketName, isFullTicker, baseCurrency, null));
      } else if (counterCurrencyName != null){
        Currency counterCurrency = Currency.getInstance(counterCurrencyName);
        result.addAll(getTickerForMarket(marketName, isFullTicker, null, counterCurrency));  //return ticker for all supported currencies
      } else {
        result.addAll(getTickerForMarket(marketName, isFullTicker, null, null));  //return tickers for all supported currencies
      }

      return result;
    } catch (Exception e) {
      LOG.error(e);
      return Collections.emptyList();
    }
  }

  private List<TickerObject> getTickerForMarket(String marketName, boolean isFullTicker, Currency baseCurrency,
                                                Currency counterCurrency) {
    List<TickerObject> result = new ArrayList<>();
    Collection<CurrencyPair> supportedCurrencyPairs = filterSupportedCurrencies(supportedCurrencies(marketName), baseCurrency, counterCurrency);
    if (isFullTicker) {
      for (CurrencyPair pair : supportedCurrencyPairs) {
        Ticker ticker = getTicker(marketName, pair);
        if (ticker != null) {
          result.add(TickerFactory.getTickerFullLayoutObject(ticker));
        }
      }
    } else {
      for (CurrencyPair pair : supportedCurrencyPairs) {
        Ticker ticker = getTicker(marketName, pair);
        if (ticker != null) {
          result.add(TickerFactory.getTickerShallowObject(ticker));
        }
      }
    }

    return result;
  }

  public Collection<Markets> supportedMarkets() {
    Collection<Markets> markets = trading.getSupportedMarkets();
    return markets;
  }

  private Ticker getTicker(String marketName, CurrencyPair pair) {
      return trading.getTicker(marketName, pair);
  }
}

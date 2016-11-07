package com.github.bitcharts.spring_boot;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.model.TickerShallowObject;
import com.github.bitcharts.trading.XChangeTrading;

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

  public Collection<CurrencyPair> supportedCurrencies(String marketName ) {
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

  public List<TickerObject> ticker(String marketName,String baseCurrency, String counterCurrency, boolean isFullTicker) {
    marketName = marketName.toUpperCase();
    try {
      List<TickerObject> result = new ArrayList<>();
      if (baseCurrency != null && counterCurrency != null) { //return the ticker for one currency
        CurrencyPair pair = new CurrencyPair(Currency.getInstance(baseCurrency), Currency.getInstance(counterCurrency));
        Ticker ticker = getTicker(marketName, pair);
        if (isFullTicker) {
          result.add(TickerFactory.getTickerFullLayoutObject(ticker));
        } else {
          result.add(TickerFactory.getTickerShallowObject(ticker));
        }
      } else {
        Collection<CurrencyPair> supportedCurrencyPairs = supportedCurrencies(marketName);

        if (isFullTicker) {
          for (CurrencyPair pair : supportedCurrencyPairs) {
            Ticker ticker = getTicker(marketName, pair);
            result.add(TickerFactory.getTickerFullLayoutObject(ticker));
          }
        } else {
          for (CurrencyPair pair : supportedCurrencyPairs) {
            Ticker ticker = getTicker(marketName, pair);
            result.add(TickerFactory.getTickerShallowObject(ticker));
          }
        }
      }

      return result;
    } catch (Exception e) {
      LOG.error(e);
      return Collections.emptyList();
    }
  }

  public Collection<Markets> supportedMarkets() {
    Collection<Markets> markets = trading.getSupportedMarkets();
    return markets;
  }

  public List<TickerObject> ticker(String marketName) {
    return this.ticker(marketName, null, null, false);
  }

  public List<TickerObject> ticker(String marketName, String baseCurrencyName, String counterCurrencyName) {
    return this.ticker(marketName, baseCurrencyName, counterCurrencyName, false);
  }

  private Ticker getTicker(@RequestParam(value = "market") String marketName, CurrencyPair pair) {
    try {
      return trading.getTicker(marketName, pair);
    } catch (Exception e) {
      return null;
    }
  }

  private List<TickerShallowObject> prepareTickerShallowObjects(String marketName) {
    //setup the list of variables to be returned
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.FTC, Currency.USD);
    return getTickerShallowObjects(marketName, supportedCurrencyPair1, supportedCurrencyPair2);
  }

  private List<TickerShallowObject> getTickerShallowObjects(String marketName, CurrencyPair... supportedCurrencyPairs) {
    List<CurrencyPair> supportedCurrencies = Arrays.asList(supportedCurrencyPairs);
    //create the assert result
    List<TickerShallowObject> result = new ArrayList<>();

    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(supportedCurrencies));

    for (CurrencyPair pair : supportedCurrencies) {
      Ticker.Builder builder = new Ticker.Builder().ask(new BigDecimal(0)).bid(new BigDecimal(0))
          .high(new BigDecimal(0)).last(new BigDecimal(0)).low(new BigDecimal(0))
          .volume(new BigDecimal(0)).vwap(new BigDecimal(0)).currencyPair(pair).timestamp(new Date());
      Ticker ticker = builder.build();

      when(trading.getTicker(marketName, pair)).thenReturn(ticker);

      result.add(TickerFactory.getTickerShallowObject(ticker));
    }

    return result;
  }

}

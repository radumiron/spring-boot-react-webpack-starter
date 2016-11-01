package com.github.bitcharts.spring_boot;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.model.TradesShallowObject;
import com.github.bitcharts.trading.XChangeTrading;
import com.github.bitcharts.trading.interfaces.TradeInterface;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

/**
 * Created by Radu on 10/28/2016.
 */
@Controller
@RequestMapping("/markets")
@ComponentScan("com.github.bitcharts.trading")
public class MarketsController {

  @Autowired
  private XChangeTrading trading;

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<CurrencyPair> supportedCurrencies(@RequestParam(value="market") String marketName ) {
    marketName = marketName.toUpperCase();
    try {
      return trading.getExchangeSymbols(Markets.valueOf(marketName));
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  @RequestMapping(value={"/ticker"}, method = RequestMethod.GET)
  public @ResponseBody List<TickerObject> ticker(@RequestParam(value="market") String marketName,
                                                 @RequestParam(value="currency", required = false) String currency,
                                                 @RequestParam(value="fullTicker", required = false, defaultValue = "false") boolean isFullTicker) {
    marketName = marketName.toUpperCase();
    try {
      List<TickerObject> result = new ArrayList<>();
      if (currency != null) { //return the ticker for one currency
        CurrencyPair pair = new CurrencyPair(Currency.BTC, Currency.getInstance(currency));
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
      e.printStackTrace();
      return null;
    }
  }

  private Ticker getTicker(@RequestParam(value = "market") String marketName, CurrencyPair pair) {
    try {
      return trading.getTicker(Markets.valueOf(marketName), pair);
    } catch (Exception e) {
      return null;
    }
  }

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody Collection<Markets> supportedMarkets() {
    return trading.getSupportedMarkets();
  }

  public void setTrading(XChangeTrading trading) {
    this.trading = trading;
  }

  public XChangeTrading getTrading() {
    return trading;
  }
}

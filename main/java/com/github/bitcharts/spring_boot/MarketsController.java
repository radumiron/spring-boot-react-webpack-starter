package com.github.bitcharts.spring_boot;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.model.TradesShallowObject;
import com.github.bitcharts.trading.XChangeTrading;
import com.github.bitcharts.trading.interfaces.TradeInterface;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
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

  private static final Logger LOG = Logger.getLogger(MarketsController.class);

  @Autowired
  private XChangeTrading trading;

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<CurrencyPair> supportedCurrencies(@RequestParam(value="market") String marketName ) {
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

  public List<TickerObject> ticker(String marketName) {
    return this.ticker(marketName, null, null, false);
  }

  public List<TickerObject> ticker(String marketName, String baseCurrencyName, String counterCurrencyName) {
    return this.ticker(marketName, baseCurrencyName, counterCurrencyName, false);
  }


  @RequestMapping(value={"/ticker"}, method = RequestMethod.GET)
  public @ResponseBody List<TickerObject> ticker(@RequestParam(value="market") String marketName,
                                                 @RequestParam(value="baseCurrency", required = false) String baseCurrency,
                                                 @RequestParam(value="counterCurrency", required = false) String counterCurrency,
                                                 @RequestParam(value="fullTicker", required = false, defaultValue = "false") boolean isFullTicker) {
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

  private Ticker getTicker(@RequestParam(value = "market") String marketName, CurrencyPair pair) {
    try {
      return trading.getTicker(marketName, pair);
    } catch (Exception e) {
      return null;
    }
  }

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody Collection<Markets> supportedMarkets() {
    Collection<Markets> markets = trading.getSupportedMarkets();
    return markets;
  }

}

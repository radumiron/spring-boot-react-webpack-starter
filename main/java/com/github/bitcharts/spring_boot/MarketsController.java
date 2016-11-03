package com.github.bitcharts.spring_boot;

import static org.mockito.Mockito.when;

import com.github.bitcharts.model.*;
import com.github.bitcharts.trading.XChangeTrading;
import com.github.bitcharts.trading.interfaces.TradeInterface;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.PostConstruct;

/**
 * Created by Radu on 10/28/2016.
 */
@Controller
@RequestMapping("/markets")
@ComponentScan("com.github.bitcharts.trading")
@RunWith(MockitoJUnitRunner.class)
public class MarketsController {

  private static final Logger LOG = Logger.getLogger(MarketsController.class);

  @Mock
  private XChangeTrading trading;

  @PostConstruct
  public void initializeMock(){
    MockitoAnnotations.initMocks(this);
    setupMock();
  }


  private void setupMock() {
    Set markets = new HashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));
    List supportedCurrencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.EUR), new CurrencyPair(Currency.FTC, Currency.USD));

    when(trading.getSupportedMarkets()).thenReturn(new HashSet<>(markets));
    when(trading.getExchangeSymbols(Markets.BTCE.name())).thenReturn(new ArrayList<>(supportedCurrencies));

  }

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<CurrencyPair> supportedCurrencies(@RequestParam(value="market") String marketName ) {
    setupMock();
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
    setupMock();
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
    setupMock();
    Collection<Markets> markets = trading.getSupportedMarkets();
    return markets;
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

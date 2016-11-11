package com.github.bitcharts.spring_boot.mock;

import static org.mockito.Mockito.when;

import java.util.*;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.model.rest.MarketsJSON;
import com.github.bitcharts.spring_boot.MarketsService;
import com.github.bitcharts.trading.XChangeTrading;
import com.github.bitcharts.trading.util.TradingJSONConverter;

/**
 * Created by mironr on 11/7/2016.
 */
@Controller
@RequestMapping("/mock_markets")
@ComponentScan("com.github.bitcharts.trading")
@RunWith(MockitoJUnitRunner.class)
public class MockMarketsController {

  @Mock
  private XChangeTrading trading;

  @InjectMocks
  private MarketsService marketsService;

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
  public @ResponseBody Collection<CurrencyPair> supportedCurrencies(@RequestParam(value = "market") String marketName) {
    setupMock();
    return marketsService.supportedCurrencies(marketName);
  }

  @RequestMapping(value={"/ticker"}, method = RequestMethod.GET)
  public @ResponseBody List<TickerObject> ticker(@RequestParam(value = "market") String marketName, @RequestParam(value = "baseCurrency",
      required = false) String baseCurrency, @RequestParam(value = "counterCurrency", required = false) String counterCurrency, @RequestParam(value = "fullTicker", required = false, defaultValue = "false") boolean isFullTicker) {
    setupMock();
    return marketsService.ticker(marketName, baseCurrency, counterCurrency, isFullTicker);
  }

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody Map<String, Collection<MarketsJSON>> supportedMarkets() {
    setupMock();
    Map<String, Collection<MarketsJSON>> result = new LinkedHashMap<>();
    result.put("markets", TradingJSONConverter.convertMarkets(marketsService.supportedMarkets()));

    return result;
  }
}

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "http://localhost:8080")
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
    List supportedCurrenciesForBTC = Arrays.asList(Currency.USD, Currency.EUR, Currency.JPY, Currency.CNY);
    List supportedCurrenciesForLTC = Arrays.asList(Currency.USD);

    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair3 = new CurrencyPair(Currency.LTC, Currency.CHF);
    CurrencyPair supportedCurrencyPair1_1 = new CurrencyPair(Currency.EUR, Currency.BTC);
    CurrencyPair supportedCurrencyPair2_1 = new CurrencyPair(Currency.USD, Currency.BTC);
    CurrencyPair supportedCurrencyPair3_1 = new CurrencyPair(Currency.CHF, Currency.LTC);

    CurrencyPair supportedCurrencyPair4 = new CurrencyPair(Currency.BTC, Currency.RON);
    CurrencyPair supportedCurrencyPair5 = new CurrencyPair(Currency.BTC, Currency.CHF);
    CurrencyPair supportedCurrencyPair4_1 = new CurrencyPair(Currency.RON, Currency.BTC);
    CurrencyPair supportedCurrencyPair5_1 = new CurrencyPair(Currency.CHF, Currency.BTC);

    List<CurrencyPair> supportedCurrencyForBTCE = Arrays.asList(supportedCurrencyPair1, supportedCurrencyPair2, supportedCurrencyPair3,
        supportedCurrencyPair1_1, supportedCurrencyPair2_1, supportedCurrencyPair3_1);
    List<CurrencyPair> supportedCurrencyForKraken = Arrays.asList(supportedCurrencyPair3, supportedCurrencyPair3_1, supportedCurrencyPair4, supportedCurrencyPair4_1,
        supportedCurrencyPair5, supportedCurrencyPair5_1);

    when(trading.getSupportedMarkets()).thenReturn(new HashSet<>(markets));
    when(trading.getExchangeSymbols(Markets.KRAKEN.name())).thenReturn(new ArrayList<>(supportedCurrencyForKraken));
    when(trading.getExchangeSymbols(Markets.BTCE.name())).thenReturn(new ArrayList<>(supportedCurrencyForBTCE));

    when(trading.getAllSupportedFiatCurrencies(Currency.BTC.getCurrencyCode())).thenReturn(supportedCurrenciesForBTC);
    when(trading.getAllSupportedFiatCurrencies(Currency.LTC.getCurrencyCode())).thenReturn(supportedCurrenciesForLTC);


  }

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<CurrencyPair> supportedCurrencies(@RequestParam(value = "market") String marketName) {
    setupMock();
    return marketsService.supportedCurrencies(marketName);
  }

  @RequestMapping(value={"/all_currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<Currency> supportedFiatCurrencies(@RequestParam(value = "cryptoCurrency", required = false) String
                                                                              cryptoCurrency) {
    setupMock();
    return marketsService.allSupportedFiatCurrencies(cryptoCurrency);
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

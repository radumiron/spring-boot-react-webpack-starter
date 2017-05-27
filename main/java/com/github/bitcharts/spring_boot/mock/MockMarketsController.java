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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.model.rest.CurrencyMixIn;
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
public class MockMarketsController extends AbstractMockController {

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<CurrencyPair> supportedCurrencies(@RequestParam(value = "market") String marketName) {
    setupMock();
    return marketsService.supportedCurrencies(marketName);
  }

  @RequestMapping(value={"/all_fiat_currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<Currency> supportedFiatCurrencies(@RequestParam(value = "cryptoCurrency", required = false) String
                                                                        cryptoCurrency) {
    setupMock();
    return marketsService.allSupportedFiatCurrencies(cryptoCurrency);
  }

  @RequestMapping(value={"/all_crypto_currencies"}, method= RequestMethod.GET)
  public @ResponseBody Collection<Currency> supportedCryptoCurrencies(@RequestParam(value = "fiatCurrency", required = false) String
                                                                        cryptoCurrency) {
    setupMock();
    return marketsService.allSupportedCryptoCurrencies(cryptoCurrency);
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

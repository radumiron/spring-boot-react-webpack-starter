package com.github.bitcharts.spring_boot;

import java.util.Collection;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerObject;

/**
 * Created by mironr on 11/7/2016.
 */
@Controller
@RequestMapping("/markets")
@ComponentScan("com.github.bitcharts.trading")
public class MarketsController {

  @Autowired
  private MarketsService marketsService;

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody
  Collection<CurrencyPair> supportedCurrencies(@RequestParam(value="market") String marketName ) {
    return marketsService.supportedCurrencies(marketName);
  }

  @RequestMapping(value={"/ticker"}, method = RequestMethod.GET)
  public @ResponseBody
  List<TickerObject> ticker(@RequestParam(value="market") String marketName,
                            @RequestParam(value="baseCurrency", required = false) String baseCurrency,
                            @RequestParam(value="counterCurrency", required = false) String counterCurrency,
                            @RequestParam(value="fullTicker", required = false, defaultValue = "false") boolean isFullTicker) {
    return marketsService.ticker(marketName, baseCurrency, counterCurrency, isFullTicker);
  }

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody Collection<Markets> supportedMarkets() {
    return marketsService.supportedMarkets();
  }

}

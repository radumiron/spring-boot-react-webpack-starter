package com.github.bitcharts.spring_boot;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerObject;

/**
 * Created by mironr on 11/7/2016.
 */
@RestController
@RequestMapping("/markets")
@ComponentScan("com.github.bitcharts.trading")
public class MarketsController {

  @Autowired
  private MarketsService marketsService;

  @RequestMapping(value={"/currencies"}, method= RequestMethod.GET)
  public @ResponseBody
  Map<String, Collection<CurrencyPair>> supportedCurrencies(@RequestParam(value="market") String marketName ) {
    Map<String, Collection<CurrencyPair>> result = new LinkedHashMap<>();

    result.put("currencies", marketsService.supportedCurrencies(marketName));
    return result;
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
  public @ResponseBody
  Map<String, Collection<Markets>> supportedMarkets() {
    Map<String, Collection<Markets>> result = new LinkedHashMap<>();
    result.put("markets", marketsService.supportedMarkets());

    return result;
  }

}

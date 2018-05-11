package com.github.bitcharts.spring_boot;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.bitcharts.model.MarketsModel;
import com.github.bitcharts.model.Markets;

/**
 * Created by Radu on 5/24/2017.
 */
@RestController
@RequestMapping("/arbitrage")
@ComponentScan("com.github.bitcharts.trading")
public class ArbitrageController {

  @Autowired
  private ArbitrageService arbitrageService;

  @RequestMapping(value={"/markets_for_fiat_currency"}, method= RequestMethod.GET)
  public @ResponseBody
  Map<String, Collection<Markets>> getArbitrageMarkets(@RequestParam(value = "fiatCurrency") String fiatCurrency) {
    Map<String, Collection<Markets>> result = new LinkedHashMap<>();
    result.put("markets", arbitrageService.getMarketsForFiatCurrency(fiatCurrency));

    return result;
  }

  @RequestMapping(value={"/arbitrage_for_fiat_currency"}, method= RequestMethod.GET)
  public @ResponseBody
  Map<String, Collection<MarketsModel>> getArbitrageData(@RequestParam(value = "fiatCurrency") String fiatCurrency) {
    Map<String, Collection<MarketsModel>> result = new LinkedHashMap<>();
    result.put("arbitrageData", arbitrageService.getArbitrageDataForCurrency(fiatCurrency));

    return result;
  }
}

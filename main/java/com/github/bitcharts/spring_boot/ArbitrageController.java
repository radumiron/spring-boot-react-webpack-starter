package com.github.bitcharts.spring_boot;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.bitcharts.model.Markets;

/**
 * Created by Radu on 5/24/2017.
 */
@Controller
@RequestMapping("/arbitrage")
@ComponentScan("com.github.bitcharts.trading")
public class ArbitrageController {

  @Autowired
  private ArbitrageService arbitrageService;

  @RequestMapping(value={"/markets_for_fiat_currency"})
  public @ResponseBody
  Map<String, Collection<Markets>> getArbitrageData(@RequestParam(value = "fiatCurrency") String fiatCurrency) {
    Map<String, Collection<Markets>> result = new LinkedHashMap<>();
    result.put("markets", arbitrageService.getMarketsForFiatCurrency(fiatCurrency));

    return result;
  }
}

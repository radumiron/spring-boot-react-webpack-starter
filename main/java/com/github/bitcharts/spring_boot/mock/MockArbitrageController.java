package com.github.bitcharts.spring_boot.mock;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.bitcharts.model.Markets;

/**
 * Created by Radu on 5/27/2017.
 */
@Controller
@RequestMapping("/mock_arbitrage")
@ComponentScan("com.github.bitcharts.trading")
@RunWith(MockitoJUnitRunner.class)
@CrossOrigin(origins = "http://localhost:8080")
public class MockArbitrageController extends AbstractMockController {

  @RequestMapping(value={"/markets_for_fiat_currency"})
  public @ResponseBody
  Set<Markets> getArbitrageData(@RequestParam(value = "market") String fiatCurrency) {
    /*List<Markets> supportedMarkets = new ArrayList<>();
    for (Markets market : marketsService.supportedMarkets()) {
      String marketName = market.name();
      supportedMarkets.addAll(marketsService.supportedCurrencies(marketName)
          .stream()
          .filter(currencyPair -> currencyPair.base.equals(fiatCurrency))
          .map(currencyPair -> market)
          .collect(Collectors.toList()));
    }*/

    return trading.getSupportedMarkets()
        .parallelStream()
        .map(markets -> trading.getExchangeSymbols(markets.name())
            .parallelStream()
            .filter(currencyPair -> currencyPair.base.equals(Currency.getInstance(fiatCurrency)))
            .map(currencyPair -> markets)
            .collect(Collectors.toSet()))
        .reduce(new LinkedHashSet<>(),
            (originalSet, currencySet) -> {
              originalSet.addAll(currencySet);
              return originalSet;
            });
    /*return marketsService.supportedMarkets()
        .parallelStream()
        .map(market -> marketsService.supportedCurrencies(market.name())
            .parallelStream()
            .filter(currencyPair -> currencyPair.base.equals(Currency.getInstance(fiatCurrency)))
            .map(currencyPair -> market).findAny().orElse(null))
        .collect(Collectors.toSet());*/
  }

}

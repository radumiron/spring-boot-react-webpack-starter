package com.github.bitcharts.spring_boot.mock;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
  Map<String, Collection<Markets>> getArbitrageData(@RequestParam(value = "fiatCurrency", required = false) String fiatCurrency,
                                                    @RequestParam(value = "cryptoCurrency", required = false) String cryptoCurrency) {
    setupMock();
    cryptoCurrency = "BTC";
    Map<String, Collection<Markets>> result = new LinkedHashMap<>();

    Set<Markets> supportedMarkets = trading.getSupportedMarkets();

    if (StringUtils.isEmpty(fiatCurrency)) {  //return all supported markets in case there's no fiatCurrency mentioned
      result.put("markets", supportedMarkets);
    } else {
      result.put("markets", arbitrageService.getMarketsForFiatCurrency(fiatCurrency));
    }

    return result;
  }

}

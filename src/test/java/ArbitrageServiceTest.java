import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.model.*;
import com.github.bitcharts.spring_boot.ArbitrageService;

/**
 * Created by Radu on 5/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = ArbitrageService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArbitrageServiceTest extends AbstractServiceTest{

  @InjectMocks
  private ArbitrageService arbitrageService;


  @Test
  public void testGetMarketsForFiatCurrency() {
    Set<Markets> markets = new LinkedHashSet<>();
    markets.add(Markets.BTCE);

    assertEquals(markets, arbitrageService.getMarketsForFiatCurrency(Currency.USD.getCurrencyCode()));
  }

  @Test
  public void testGetMarketsForCryptoCurrency() {
    Set<Markets> markets = new LinkedHashSet<>();
    markets.add(Markets.BTCE);
    markets.add(Markets.KRAKEN);

    assertEquals(markets, arbitrageService.getMarketsForFiatCurrency(Currency.LTC.getCurrencyCode()));
  }

  @Test
  public void testArbitrageData() {
    Set<Markets> markets = new LinkedHashSet<>();
    markets.add(Markets.BTCE);
    markets.add(Markets.KRAKEN);

    //setup the list of variables to be returned
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    List<TickerObject> resultKraken = getTickerObjects(KRAKEN_MARKET, true, supportedCurrencyPair1);

    //setup the list of variables to be returned
    List<TickerObject> resultBTCE = getTickerObjects(BTCE_MARKET, true, supportedCurrencyPair1);

    Set<MarketsModel> result = arbitrageService.getArbitrageDataForCurrency(Currency.EUR.getCurrencyCode());
    for (MarketsModel model : result) {
      if (Markets.BTCE.equals(model.getMarket())) {
        List<TickerShallowObject> compareResult = model.getMarketsModels()
            .stream()
            .filter(marketsModel -> Markets.BTCE.equals(marketsModel.getMarket()))
            .map(MarketsModel::getTicker)
            .collect(Collectors.toList());
        assertEquals(resultBTCE, compareResult);
      }
      if (Markets.KRAKEN.equals(model.getMarket())) {
        List<TickerShallowObject> compareResult = model.getMarketsModels()
            .stream()
            .filter(marketsModel -> Markets.KRAKEN.equals(marketsModel.getMarket()))
            .map(MarketsModel::getTicker)
            .collect(Collectors.toList());

        assertEquals(resultKraken, compareResult);
      }
    }
  }

}

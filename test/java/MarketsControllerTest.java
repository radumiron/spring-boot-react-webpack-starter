import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.spring_boot.MarketsController;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by mironr on 11/1/2016.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MarketsController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarketsControllerTest {

  @Mock
  private XChangeTrading trading;

  @InjectMocks
  private MarketsController controller;

  @Test
  public void testExchanges() {
    Set markets = new HashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));
    when(trading.getSupportedMarkets()).thenReturn(new HashSet<>(markets));

    assertEquals(markets, controller.supportedMarkets());
  }

  @Test
  public void testSupportedCurrencies() {
    String marketName = "KRAKEN";
    List supportedCurrencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.EUR), new CurrencyPair(Currency.FTC, Currency.USD));
    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(supportedCurrencies));

    assertEquals(supportedCurrencies, controller.supportedCurrencies(marketName));

    List unsupportedCurrencies = Arrays.asList(new CurrencyPair(Currency.FTC, Currency.RON));
    assertNotEquals(unsupportedCurrencies, controller.supportedCurrencies(marketName));


  }

  @Test
  public void testUnsupportedMarketName() {
    String marketName = "MTGOX";
    List markets = Collections.emptyList();
    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(markets));
    assertEquals(markets, controller.supportedCurrencies(marketName));
  }

}

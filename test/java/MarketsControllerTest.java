import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerShallowObject;
import com.github.bitcharts.trading.util.TradingUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
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


  }

  @Test
  public void testUnsupportedCurrencies() {
    String marketName = "KRAKEN";

    List supportedCurrencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.EUR), new CurrencyPair(Currency.FTC, Currency.USD));
    List unsupportedCurrencies = Arrays.asList(new CurrencyPair(Currency.FTC, Currency.RON));

    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(supportedCurrencies));
    assertNotEquals(unsupportedCurrencies, controller.supportedCurrencies(marketName));
  }

  @Test
  public void testUnsupportedMarketName() {
    String marketName = "MTGOX";

    List currencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.USD));

    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(currencies));
    assertNotEquals(currencies, controller.supportedCurrencies(marketName));
  }

  @Test
  public void testTickerWithValidMarketName() {
    String marketName = "KRAKEN";
    List<TickerShallowObject> result = prepareTickerShallowObjects(marketName);

    assertEquals(result, controller.ticker(marketName));
  }

  @Test
  public void testTickerWithInvalidMarketName() {
    String marketName = "MTGOX";

    //setup the list of variables to be returned
    List<TickerShallowObject> result = prepareTickerShallowObjects(marketName);

    assertNotEquals(result, controller.ticker(marketName));
  }

  @Test
  public void testTickerWithValidBaseAndCounterCurrency() {
    String marketName = "KRAKEN";
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerShallowObject> result = getTickerShallowObjects(marketName, supportedCurrencyPair);

    assertEquals(result, controller.ticker(marketName, supportedCurrencyPair.base.getDisplayName(),
            supportedCurrencyPair.counter.getDisplayName()));
  }


  private List<TickerShallowObject> prepareTickerShallowObjects(String marketName) {
    //setup the list of variables to be returned
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.FTC, Currency.USD);
    return getTickerShallowObjects(marketName, supportedCurrencyPair1, supportedCurrencyPair2);
  }

  private List<TickerShallowObject> getTickerShallowObjects(String marketName, CurrencyPair... supportedCurrencyPairs) {
    List<CurrencyPair> supportedCurrencies = Arrays.asList(supportedCurrencyPairs);
    //create the assert result
    List<TickerShallowObject> result = new ArrayList<>();

    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(supportedCurrencies));

    for (CurrencyPair pair : supportedCurrencies) {
      Ticker.Builder builder = new Ticker.Builder().ask(new BigDecimal(0)).bid(new BigDecimal(0))
              .high(new BigDecimal(0)).last(new BigDecimal(0)).low(new BigDecimal(0))
              .volume(new BigDecimal(0)).vwap(new BigDecimal(0)).currencyPair(pair).timestamp(new Date());
      Ticker ticker = builder.build();

      when(trading.getTicker(marketName, pair)).thenReturn(ticker);

      result.add(TickerFactory.getTickerShallowObject(ticker));
    }

    return result;
  }


}

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerShallowObject;
import com.github.bitcharts.spring_boot.MarketsService;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by mironr on 11/1/2016.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MarketsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarketsServiceTest {

  @Mock
  private XChangeTrading trading;

  @InjectMocks
  private MarketsService controller;

  private static final String KRAKEN_MARKET = "KRAKEN";
  public static final String MTGOX_MARKET = "MTGOX";

  @Test
  public void testExchanges() {
    Set markets = new HashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));
    when(trading.getSupportedMarkets()).thenReturn(new HashSet<>(markets));

    assertEquals(markets, controller.supportedMarkets());
  }

  @Test
  public void testSupportedCurrencies() {
    List supportedCurrencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.EUR), new CurrencyPair(Currency.FTC, Currency.USD));
    when(trading.getExchangeSymbols(KRAKEN_MARKET)).thenReturn(new ArrayList<>(supportedCurrencies));

    assertEquals(supportedCurrencies, controller.supportedCurrencies(KRAKEN_MARKET));


  }

  @Test
  public void testUnsupportedCurrencies() {
    List supportedCurrencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.EUR), new CurrencyPair(Currency.FTC, Currency.USD));
    List unsupportedCurrencies = Arrays.asList(new CurrencyPair(Currency.FTC, Currency.RON));

    when(trading.getExchangeSymbols(KRAKEN_MARKET)).thenReturn(new ArrayList<>(supportedCurrencies));
    assertNotEquals(unsupportedCurrencies, controller.supportedCurrencies(KRAKEN_MARKET));
  }

  @Test
  public void testUnsupportedMarketName() {
    //List currencies = Arrays.asList(new CurrencyPair(Currency.BTC, Currency.USD));

    //when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(currencies));
    assertEquals(new ArrayList<>(), controller.supportedCurrencies(MTGOX_MARKET));
  }

  @Test
  public void testTickerWithValidMarketName() {
    List<TickerShallowObject> result = prepareTickerShallowObjects(KRAKEN_MARKET);

    assertEquals(result, controller.ticker(KRAKEN_MARKET));
  }

  @Test
  public void testTickerWithInvalidMarketName() {
    //setup the list of variables to be returned
    List<TickerShallowObject> result = prepareTickerShallowObjects(MTGOX_MARKET);

    assertNotEquals(result, controller.ticker(MTGOX_MARKET));
  }

  @Test
  public void testTickerWithValidBaseAndCounterCurrency() {
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerShallowObject> result = getTickerShallowObjects(KRAKEN_MARKET, supportedCurrencyPair);

    assertEquals(result, controller.ticker(KRAKEN_MARKET, supportedCurrencyPair.base.getCurrencyCode(),
            supportedCurrencyPair.counter.getCurrencyCode()));
  }

  @Test
  public void testTickerWithValidBaseCurrencyAndInvalidCounterCurrency() {
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerShallowObject> result = getTickerShallowObjects(KRAKEN_MARKET, supportedCurrencyPair);

    assertNotEquals(result, controller.ticker(KRAKEN_MARKET, supportedCurrencyPair.base.getCurrencyCode(),
        Currency.AED.getCurrencyCode()));
  }

  @Test
  public void testTickerWithInvalidBaseCurrencyAndValidCounterCurrency() {
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerShallowObject> result = getTickerShallowObjects(KRAKEN_MARKET, supportedCurrencyPair);

    assertNotEquals(result, controller.ticker(KRAKEN_MARKET, Currency.BTC.getCurrencyCode(),
        supportedCurrencyPair.base.getCurrencyCode()));
  }

  @Test
  public void testTickerForBaseCurrencyOnly() {
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair3 = new CurrencyPair(Currency.BTC, Currency.RON);

    List<TickerShallowObject> correctResults = getTickerShallowObjects(KRAKEN_MARKET, supportedCurrencyPair1,
        supportedCurrencyPair2, supportedCurrencyPair3);

    assertEquals(correctResults, controller.ticker(KRAKEN_MARKET, Currency.BTC.getCurrencyCode(), null));
  }

  @Test
  public void testTickerForCounterCurrencyOnly() {
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair4 = new CurrencyPair(Currency.FTC, Currency.USD);

    List<TickerShallowObject> correctResults = getTickerShallowObjects(KRAKEN_MARKET, supportedCurrencyPair2,
        supportedCurrencyPair4);

    assertEquals(correctResults, controller.ticker(KRAKEN_MARKET, null, Currency.USD.getCurrencyCode()));
  }

  /*@Test
  public void testTickerForBaseCurrencyOnly() {
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair3 = new CurrencyPair(Currency.BTC, Currency.RON);
    CurrencyPair supportedCurrencyPair4 = new CurrencyPair(Currency.FTC, Currency.USD);
    CurrencyPair supportedCurrencyPair5 = new CurrencyPair(Currency.FTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair6 = new CurrencyPair(Currency.FTC, Currency.RON);

    List<TickerShallowObject> correctResults = getTickerShallowObjects(KRAKEN_MARKET, supportedCurrencyPair1,
        supportedCurrencyPair2, supportedCurrencyPair3);

    assertEquals(correctResults, controller.ticker(KRAKEN_MARKET, Currency.BTC.getCurrencyCode(), null));
  }*/


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

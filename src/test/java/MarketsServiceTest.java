import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.spring_boot.MarketsService;

/**
 * Created by mironr on 11/1/2016.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MarketsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarketsServiceTest extends AbstractServiceTest {

  @InjectMocks
  private MarketsService controller;

  @Test
  public void testExchanges() {
    Set markets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));
    when(trading.getSupportedMarkets()).thenReturn(new LinkedHashSet<>(markets));

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
    List<TickerObject> result = prepareTickerShallowObjects(KRAKEN_MARKET);

    assertEquals(result, controller.ticker(KRAKEN_MARKET));
  }

  @Test
  public void testTickerWithInvalidMarketName() {
    //setup the list of variables to be returned
    List<TickerObject> result = prepareTickerShallowObjects(MTGOX_MARKET);

    assertNotEquals(result, controller.ticker(MTGOX_MARKET));
  }

  @Test
  public void testTickerWithValidBaseAndCounterCurrency() {
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerObject> result = getTickerObjects(KRAKEN_MARKET, false, supportedCurrencyPair);

    assertEquals(result, controller.ticker(KRAKEN_MARKET, supportedCurrencyPair.base.getCurrencyCode(),
            supportedCurrencyPair.counter.getCurrencyCode()));
  }

  @Test
  public void testTickerWithValidBaseCurrencyAndInvalidCounterCurrency() {
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerObject> result = getTickerObjects(KRAKEN_MARKET, false, supportedCurrencyPair);

    assertNotEquals(result, controller.ticker(KRAKEN_MARKET, supportedCurrencyPair.base.getCurrencyCode(),
        Currency.AED.getCurrencyCode()));
  }

  @Test
  public void testTickerWithInvalidBaseCurrencyAndValidCounterCurrency() {
    CurrencyPair supportedCurrencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);

    List<TickerObject> result = getTickerObjects(KRAKEN_MARKET, false, supportedCurrencyPair);

    assertNotEquals(result, controller.ticker(KRAKEN_MARKET, Currency.BTC.getCurrencyCode(),
        supportedCurrencyPair.base.getCurrencyCode()));
  }

  @Test
  public void testTickerForBaseCurrencyOnly() {
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair3 = new CurrencyPair(Currency.BTC, Currency.RON);

    List<TickerObject> correctResults = getTickerObjects(KRAKEN_MARKET, false, supportedCurrencyPair1,
        supportedCurrencyPair2, supportedCurrencyPair3);

    assertEquals(correctResults, controller.ticker(KRAKEN_MARKET, Currency.BTC.getCurrencyCode(), null));
  }

  @Test
  public void testTickerForCounterCurrencyOnly() {
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair4 = new CurrencyPair(Currency.FTC, Currency.USD);

    List<TickerObject> correctResults = getTickerObjects(KRAKEN_MARKET, false, supportedCurrencyPair2,
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

    List<TickerShallowObject> correctResults = getTickerObjects(KRAKEN_MARKET, supportedCurrencyPair1,
        supportedCurrencyPair2, supportedCurrencyPair3);

    assertEquals(correctResults, controller.ticker(KRAKEN_MARKET, Currency.BTC.getCurrencyCode(), null));
  }*/


  @Before
  public void beforeTestCurrencies() {
    constructCurrencyPairs();
  }
  @Test
  public void testGetAllSupportedFiatCurrencies() {
    Set<Currency> correctResult = new LinkedHashSet<>(Arrays.asList(Currency.EUR, Currency.USD, Currency.GBP, Currency.CHF, Currency.RON));

    assertEquals(correctResult, controller.allSupportedFiatCurrencies(null));
  }

  @Test
  public void testGetSupportedFiatCurrenciesForCryptoCurrency() {
    Set<Currency> correctResult = new LinkedHashSet<>(Arrays.asList(Currency.CHF));

    assertEquals(correctResult, controller.allSupportedFiatCurrencies("LTC"));
  }

  @Test
  public void testGetSupportedFiatCurrenciesForCryptoCurrency2() {
    Set<Currency>correctResult = new LinkedHashSet<>(Arrays.asList(Currency.USD, Currency.GBP, Currency.EUR, Currency.RON));

    assertEquals(true, correctResult.equals(controller.allSupportedFiatCurrencies("BTC")));
  }

  @Test
  public void testGetSupportedFiatCurrenciesForInvalidCryptoCurrency() {
    Set<Currency> incorrectResult = new LinkedHashSet<>(Arrays.asList(Currency.CHF));

    assertNotEquals(incorrectResult, controller.allSupportedFiatCurrencies("EUR"));
  }

  @Test
  public void testGetSupportedFiatCurrenciesForInvalidCryptoCurrency2() {
    Set<Currency> incorrectResult = new LinkedHashSet<>(Arrays.asList(Currency.USD));

    assertNotEquals(incorrectResult, controller.allSupportedFiatCurrencies("GBP"));
  }

  @Test
  public void testGetAllSupportedCryptoCurrencies() {
    Set<Currency> correctResult = new LinkedHashSet<>(Arrays.asList(Currency.BTC, Currency.LTC));

    assertEquals(correctResult, controller.allSupportedCryptoCurrencies(null));
  }

  @Test
  public void testGetSupportedCryptoCurrenciesForFiatCurrency() {
    Set<Currency> correctResult = new LinkedHashSet<>(Arrays.asList(Currency.LTC));

    assertEquals(correctResult, controller.allSupportedCryptoCurrencies("CHF"));
   }

  @Test
  public void testGetSupportedCryptoCurrenciesForFiatCurrency2() {
    Set<Currency> correctResult;
    correctResult = new LinkedHashSet<>(Arrays.asList(Currency.BTC));

    assertEquals(correctResult, controller.allSupportedCryptoCurrencies("GBP"));
  }

  @Test
  public void testGetSupportedCryptoCurrenciesForInvalidFiatCurrency() {
    Set<Currency> incorrectResult = new LinkedHashSet<>(Arrays.asList(Currency.CHF));

    assertNotEquals(incorrectResult, controller.allSupportedCryptoCurrencies("AED"));
  }

  @Test
  public void testGetSupportedCryptoCurrenciesForInvalidFiatCurrency2() {
    Set<Currency> incorrectResult = new LinkedHashSet<>(Arrays.asList(Currency.UAH));

    assertNotEquals(incorrectResult, controller.allSupportedCryptoCurrencies("UAH"));
  }

}

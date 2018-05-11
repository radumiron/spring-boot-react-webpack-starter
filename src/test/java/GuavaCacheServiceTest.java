import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.cache.GoogleGuavaCacheService;
import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerObject;
import com.github.bitcharts.model.cache.*;
import com.github.bitcharts.spring_boot.MarketsService;

/**
 * Created by Radu on 8/29/2017.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MarketsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuavaCacheServiceTest extends AbstractServiceTest {

  private static final long TIME_TO_LIVE = 500;

  @InjectMocks
  GoogleGuavaCacheService cacheService;

  @Before
  public void prepare() {
    cacheService.initCache(TIME_TO_LIVE, TimeUnit.MILLISECONDS);
  }

  @After
  public void tearDown() {
    Mockito.reset(trading);
  }

  @Test
  public void testSupportedMarkets() {
    Set markets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));

    GuavaCacheKey key = new GuavaCacheKey(CacheKey.CacheKeyType.SUPPORTED_MARKETS);
    GuavaCacheEntry<Set> value = cacheService.getValue(key);

    assertEquals(markets, value.getValue());
  }

  @Test
  public void testSupportedMarketsWithoutTimeout() {
    GuavaCacheKey key = new GuavaCacheKey(CacheKey.CacheKeyType.SUPPORTED_MARKETS);
    GuavaCacheEntry<Set> initialValue = executeSupportedMarketTest(key, false);

    GuavaCacheEntry<Set> secondValue = cacheService.getValue(key);  //retrieve the entry from the cache instead out of the trading interface, as it
    // didn't expire yet
    //the initial value should the same with the second value, as the cache entry didn't expire yet
    assertEquals(initialValue.getValue(), secondValue.getValue());
  }

  @Test
  public void testSupportedMarketsWithTimeout() {
    GuavaCacheKey key = new GuavaCacheKey(CacheKey.CacheKeyType.SUPPORTED_MARKETS);
    GuavaCacheEntry<Set> initialValue = executeSupportedMarketTest(key, true);
    //retrieve the entry from the trading interface instead out of the cache, as the timeout made it expire
    GuavaCacheEntry<Set> secondValue = cacheService.getValue(key);
    assertNotEquals(initialValue.getValue(), secondValue.getValue());
  }

  private GuavaCacheEntry<Set> executeSupportedMarketTest(GuavaCacheKey key, boolean hasTimeout) {
    //Set<Markets> initialMarkets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));

    //first, get the initial list of supported markets

    GuavaCacheEntry<Set> initialValue = cacheService.getValue(key);  //this value will be the initial 2 markets (BTCE & KRAKEN)

    //then reset the value returned by the trading interface to a different set of supported markets
    Set<Markets> secondMarkets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN, Markets.ANXV3}));
    //reset the current stubbings on trading, otherwise we'll get an unnecessary stubbing exception
    Mockito.reset(trading);

    //make trading return a different set of supported markets, in order to test the cache service
    when(trading.getSupportedMarkets()).thenReturn(secondMarkets);

    if (hasTimeout) { //sleep for the amount of timeToLive + 1 second
      try {
        Thread.sleep(TIME_TO_LIVE + 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return initialValue;
  }

  @Test
  public void testTickerWithoutTimeout() {
    List<TickerObject> tickerObjectList = prepareTickerShallowObjects(KRAKEN_MARKET);
    CurrencyPair currencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);
    GuavaCacheKey key = new GuavaCacheKeyForTicker(CacheKey.CacheKeyType.TICKER, KRAKEN_MARKET, currencyPair);
    GuavaCacheEntry<Ticker> marketTicker = cacheService.getValue(key);

    assertTrue(tickerObjectList.contains(marketTicker.getValue()));
  }

  @Test
  public void testTickerWithTimeout() {
    List<TickerObject> tickerObjectList = prepareTickerShallowObjects(KRAKEN_MARKET);
    CurrencyPair currencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);
    GuavaCacheKey key = new GuavaCacheKeyForTicker(CacheKey.CacheKeyType.TICKER, KRAKEN_MARKET, currencyPair);
    GuavaCacheEntry<Ticker> marketTickerInitialValue = cacheService.getValue(key);

    try {  //sleep for the amount of timeToLive + 1 second
      Thread.sleep(TIME_TO_LIVE + 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //the original value has expired in the cache, so take the ticker value again
    GuavaCacheEntry<Ticker> marketTickerNewValue = cacheService.getValue(key);

    assertTrue(marketTickerInitialValue != marketTickerNewValue);
  }

  @Test
  public void testNullTicker() {
    CurrencyPair currencyPair = new CurrencyPair(Currency.BTC, Currency.EUR);
    GuavaCacheKey key = new GuavaCacheKeyForTicker(CacheKey.CacheKeyType.TICKER, KRAKEN_MARKET, currencyPair);
    GuavaCacheEntry<Ticker> marketTickerInitialValue = cacheService.getValue(key);

    assertNull(marketTickerInitialValue.getValue());
  }

}

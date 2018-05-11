import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.cache.Cache2KCacheService;
import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.cache.Cache2KCacheEntry;
import com.github.bitcharts.model.cache.Cache2KCacheKey;
import com.github.bitcharts.model.cache.CacheKey;
import com.github.bitcharts.spring_boot.MarketsService;

/**
 * Created by Radu on 8/29/2017.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MarketsService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Cache2KCacheServiceTest extends AbstractServiceTest {

  @InjectMocks
  Cache2KCacheService cacheService;

  @Before
  public void prepare() {
    cacheService.initCache();
  }

  @Test
  public void testSupportedMarkets() {
    Set markets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));

    Cache2KCacheKey key = new Cache2KCacheKey(CacheKey.CacheKeyType.SUPPORTED_MARKETS);
    Cache2KCacheEntry<Set> value = cacheService.getValue(key);

    assertEquals(markets, value.getValue());
  }

  @Test
  public void testSupportedMarketsWithTimeout() {

    //Set<Markets> initialMarkets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));

    //first, get the initial list of supported markets
    Cache2KCacheKey key = new Cache2KCacheKey(CacheKey.CacheKeyType.SUPPORTED_MARKETS);
    Cache2KCacheEntry<Set> initialValue = cacheService.getValue(key);  //this value will be the initial 2 markets (BTCE & KRAKEN)

    //then reset the value returned by the trading interface to a different set of supported markets
    Set<Markets> secondMarkets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN, Markets.ANXV3}));
    when(trading.getSupportedMarkets()).thenReturn(secondMarkets);


    Cache2KCacheEntry<Set> secondValue = cacheService.getValue(key);  //retrieve the entry from the cache instead out of the trading interface, as it
    // didn't expire yet
    //the initial value should the same with the second value, as the cache entry didn't expire yet
    assertEquals(initialValue.getValue(), secondValue.getValue());
  }
}

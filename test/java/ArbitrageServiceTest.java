import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.bitcharts.model.Markets;
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

}

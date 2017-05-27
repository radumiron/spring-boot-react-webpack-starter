import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by Radu on 5/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractServiceTest {

  public static final String KRAKEN_MARKET = "KRAKEN";
  public static final String BTCE_MARKET = "BTCE";
  public static final String MTGOX_MARKET = "MTGOX";

  @Mock
  protected XChangeTrading trading;

  @Before
  public void beforeTestCurrencies() {
    constructCurrencyPairs();
  }

  protected void constructCurrencyPairs() {
    Set markets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.KRAKEN, Markets.BTCE}));
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair3 = new CurrencyPair(Currency.LTC, Currency.CHF);
    CurrencyPair supportedCurrencyPair1_1 = new CurrencyPair(Currency.EUR, Currency.BTC);
    CurrencyPair supportedCurrencyPair2_1 = new CurrencyPair(Currency.USD, Currency.BTC);
    CurrencyPair supportedCurrencyPair3_1 = new CurrencyPair(Currency.CHF, Currency.LTC);

    CurrencyPair supportedCurrencyPair4 = new CurrencyPair(Currency.BTC, Currency.RON);
    CurrencyPair supportedCurrencyPair5 = new CurrencyPair(Currency.BTC, Currency.GBP);
    CurrencyPair supportedCurrencyPair4_1 = new CurrencyPair(Currency.RON, Currency.BTC);
    CurrencyPair supportedCurrencyPair5_1 = new CurrencyPair(Currency.GBP, Currency.BTC);

    List<CurrencyPair> supportedCurrencyForBTCE = Arrays.asList(supportedCurrencyPair1, supportedCurrencyPair2, supportedCurrencyPair3,
        supportedCurrencyPair1_1, supportedCurrencyPair2_1, supportedCurrencyPair3_1);
    List<CurrencyPair> supportedCurrencyForKraken = Arrays.asList(supportedCurrencyPair3, supportedCurrencyPair3_1, supportedCurrencyPair4, supportedCurrencyPair4_1,
        supportedCurrencyPair5, supportedCurrencyPair5_1);

    when(trading.getSupportedMarkets()).thenReturn(new LinkedHashSet<>(markets));
    when(trading.getExchangeSymbols(KRAKEN_MARKET)).thenReturn(new ArrayList<>(supportedCurrencyForKraken));
    when(trading.getExchangeSymbols(BTCE_MARKET)).thenReturn(new ArrayList<>(supportedCurrencyForBTCE));
  }

}

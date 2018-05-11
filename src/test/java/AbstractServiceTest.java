import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.bitcharts.model.*;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by Radu on 5/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractServiceTest {

  public static final String KRAKEN_MARKET = "KRAKEN";
  public static final String BTCE_MARKET = "BTCE";
  public static final String MTGOX_MARKET = "MTGOX";
  public static final String OK_COIN_MARKET = "OK_COIN_MARKET";

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
    CurrencyPair supportedCurrencyPair4_1_1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair5 = new CurrencyPair(Currency.BTC, Currency.GBP);
    CurrencyPair supportedCurrencyPair4_1 = new CurrencyPair(Currency.RON, Currency.BTC);
    CurrencyPair supportedCurrencyPair5_1 = new CurrencyPair(Currency.GBP, Currency.BTC);

    List<CurrencyPair> supportedCurrencyForBTCE = Arrays.asList(supportedCurrencyPair1, supportedCurrencyPair2, supportedCurrencyPair3,
        supportedCurrencyPair1_1, supportedCurrencyPair2_1, supportedCurrencyPair3_1);
    List<CurrencyPair> supportedCurrencyForKraken = Arrays.asList(supportedCurrencyPair3, supportedCurrencyPair3_1, supportedCurrencyPair4,
        supportedCurrencyPair4_1_1,
        supportedCurrencyPair4_1,
        supportedCurrencyPair5, supportedCurrencyPair5_1);

    when(trading.getSupportedMarkets()).thenReturn(new LinkedHashSet<>(markets));
    when(trading.getExchangeSymbols(KRAKEN_MARKET)).thenReturn(new ArrayList<>(supportedCurrencyForKraken));
    when(trading.getExchangeSymbols(BTCE_MARKET)).thenReturn(new ArrayList<>(supportedCurrencyForBTCE));
  }

  protected List<TickerObject> prepareTickerFullLayoutObjects(String marketName) {
    //setup the list of variables to be returned
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.FTC, Currency.USD);
    return getTickerObjects(marketName, true, supportedCurrencyPair1, supportedCurrencyPair2);
  }

  protected List<TickerObject> prepareTickerShallowObjects(String marketName) {
    //setup the list of variables to be returned
    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.FTC, Currency.USD);
    return getTickerObjects(marketName, false, supportedCurrencyPair1, supportedCurrencyPair2);
  }

  protected List<TickerObject> getTickerObjects(String marketName, boolean isFull, CurrencyPair... supportedCurrencyPairs) {
    List<CurrencyPair> supportedCurrencies = Arrays.asList(supportedCurrencyPairs);
    //create the assert result
    List<TickerObject> result = new ArrayList<>();

    when(trading.getExchangeSymbols(marketName)).thenReturn(new ArrayList<>(supportedCurrencies));

    for (int i = 0; i < supportedCurrencies.size(); i++) {
      CurrencyPair pair = supportedCurrencies.get(i);
      Ticker.Builder builder = new Ticker.Builder().ask(new BigDecimal(marketName.hashCode())).bid(new BigDecimal(marketName.hashCode()))
          .high(new BigDecimal(marketName.hashCode())).last(new BigDecimal(marketName.hashCode())).low(new BigDecimal(marketName.hashCode()))
          .volume(new BigDecimal(marketName.hashCode())).vwap(new BigDecimal(marketName.hashCode())).currencyPair(pair).timestamp(new Date());
      Ticker ticker = builder.build();

      when(trading.getTicker(marketName, pair)).thenReturn(ticker);

      if (isFull) {
        result.add(TickerFactory.getTickerFullLayoutObject(ticker));
      } else {
        result.add(TickerFactory.getTickerShallowObject(ticker));
      }
    }

    return result;
  }

}

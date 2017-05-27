package com.github.bitcharts.spring_boot.mock;

import static org.mockito.Mockito.when;

import java.util.*;

import javax.annotation.PostConstruct;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.spring_boot.ArbitrageService;
import com.github.bitcharts.spring_boot.MarketsService;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by Radu on 5/27/2017.
 */
public class AbstractMockController {

  @Mock
  protected MarketsService marketsService;

  @InjectMocks
  protected ArbitrageService arbitrageService;

  @Mock
  protected XChangeTrading trading;

  @PostConstruct
  public void initializeMock(){
    MockitoAnnotations.initMocks(this);
    setupMock();
  }

  public void setupMock() {
    Set markets = new LinkedHashSet<>(Arrays.asList(new Markets[]{Markets.BTCE, Markets.KRAKEN}));
    List supportedCurrenciesForBTC = Arrays.asList(Currency.USD, Currency.EUR, Currency.JPY, Currency.CNY);
    List supportedCurrenciesForLTC = Arrays.asList(Currency.USD);

    CurrencyPair supportedCurrencyPair1 = new CurrencyPair(Currency.BTC, Currency.EUR);
    CurrencyPair supportedCurrencyPair2 = new CurrencyPair(Currency.BTC, Currency.USD);
    CurrencyPair supportedCurrencyPair3 = new CurrencyPair(Currency.LTC, Currency.CHF);
    CurrencyPair supportedCurrencyPair1_1 = new CurrencyPair(Currency.EUR, Currency.BTC);
    CurrencyPair supportedCurrencyPair2_1 = new CurrencyPair(Currency.USD, Currency.BTC);
    CurrencyPair supportedCurrencyPair3_1 = new CurrencyPair(Currency.CHF, Currency.LTC);

    CurrencyPair supportedCurrencyPair4 = new CurrencyPair(Currency.BTC, Currency.RON);
    CurrencyPair supportedCurrencyPair5 = new CurrencyPair(Currency.BTC, Currency.CHF);
    CurrencyPair supportedCurrencyPair4_1 = new CurrencyPair(Currency.RON, Currency.BTC);
    CurrencyPair supportedCurrencyPair5_1 = new CurrencyPair(Currency.CHF, Currency.BTC);

    List<CurrencyPair> supportedCurrencyForBTCE = Arrays.asList(supportedCurrencyPair1, supportedCurrencyPair2, supportedCurrencyPair3,
        supportedCurrencyPair1_1, supportedCurrencyPair2_1, supportedCurrencyPair3_1);
    List<CurrencyPair> supportedCurrencyForKraken = Arrays.asList(supportedCurrencyPair3, supportedCurrencyPair3_1, supportedCurrencyPair4, supportedCurrencyPair4_1,
        supportedCurrencyPair5, supportedCurrencyPair5_1);

    when(trading.getSupportedMarkets()).thenReturn(new LinkedHashSet<>(markets));
    when(trading.getExchangeSymbols(Markets.KRAKEN.name())).thenReturn(new ArrayList<>(supportedCurrencyForKraken));
    when(trading.getExchangeSymbols(Markets.BTCE.name())).thenReturn(new ArrayList<>(supportedCurrencyForBTCE));

    when(trading.getAllSupportedFiatCurrencies(Currency.BTC.getCurrencyCode())).thenReturn(supportedCurrenciesForBTC);
    when(trading.getAllSupportedFiatCurrencies(Currency.LTC.getCurrencyCode())).thenReturn(supportedCurrenciesForLTC);

  }
}

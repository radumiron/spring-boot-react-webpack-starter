package com.github.bitcharts.spring_boot;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.bitcharts.model.ArbitrageModel;
import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerFactory;
import com.github.bitcharts.model.TickerFullLayoutObject;
import com.github.bitcharts.trading.XChangeTrading;

/**
 * Created by Radu on 5/27/2017.
 */
@Service
public class ArbitrageService {
  private static final Logger LOG = Logger.getLogger(MarketsService.class);

  private XChangeTrading trading;

  @Autowired
  private MarketsService marketsService;

  @Autowired
  public ArbitrageService(final XChangeTrading trading, final MarketsService marketsService) {
    this.trading = trading;
    this.marketsService = marketsService;
  }


  public Set<Markets> getMarketsForFiatCurrency(String fiatCurrency) {
    return trading.getSupportedMarkets()
        .parallelStream()
        .map(markets -> trading.getExchangeSymbols(markets.name())
            .parallelStream()
            .filter(currencyPair -> currencyPair.base.equals(Currency.getInstanceNoCreate(fiatCurrency))
              || currencyPair.counter.equals(Currency.getInstanceNoCreate(fiatCurrency)))
            .map(currencyPair -> markets)
            .collect(Collectors.toSet()))
        .reduce(new LinkedHashSet<>(),
            (originalSet, currencySet) -> {
              originalSet.addAll(currencySet);
              return originalSet;
            });
  }

  public Set<ArbitrageModel> getArbitrageDataForCurrency(String fiatCurrency) {
    Currency currency = Currency.getInstanceNoCreate(fiatCurrency);
    if (currency == null) {
      return new LinkedHashSet<>();
    }

    Set<ArbitrageModel> arbitrageModels = new LinkedHashSet<>();

    Set<Markets> markets = getMarketsForFiatCurrency(fiatCurrency);

    for (Markets market : markets) {
      ArbitrageModel model = new ArbitrageModel();

      Ticker marketTicker = trading.getTicker(market.name(), new CurrencyPair(Currency.BTC, currency));
      TickerFullLayoutObject localTicker = TickerFactory.getTickerFullLayoutObject(marketTicker);
      if (localTicker != null) {      //if valid ticker - taken from the market
        model.setTicker(localTicker);
        arbitrageModels.add(model);
      }


    }

    return arbitrageModels;
  }

}

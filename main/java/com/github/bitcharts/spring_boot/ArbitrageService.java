package com.github.bitcharts.spring_boot;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.knowm.xchange.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.bitcharts.model.Markets;
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
            .filter(currencyPair -> currencyPair.base.equals(Currency.getInstance(fiatCurrency)))
            .map(currencyPair -> markets)
            .collect(Collectors.toSet()))
        .reduce(new LinkedHashSet<>(),
            (originalSet, currencySet) -> {
              originalSet.addAll(currencySet);
              return originalSet;
            });
  }

}

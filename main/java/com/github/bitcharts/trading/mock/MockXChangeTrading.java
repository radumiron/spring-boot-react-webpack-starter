package com.github.bitcharts.trading.mock;

import java.util.Collection;
import java.util.Set;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.github.bitcharts.model.Markets;
import com.github.bitcharts.model.TickerShallowObject;
import com.github.bitcharts.model.TradesFullLayoutObject;
import com.github.bitcharts.trading.interfaces.TradeInterface;

/**
 * Created by mironr on 10/31/2016.
 */
public class MockXChangeTrading implements TradeInterface {
  @Override
  public double[] getBalance() {
    return new double[0];
  }

  @Override
  public <T extends TickerShallowObject> T getLastPrice(Markets market, CurrencyPair cur) {
    return null;
  }

  @Override
  public <T extends TickerShallowObject> T getPrice(Markets market, CurrencyPair currency) {
    return null;
  }

  @Override
  public Set<Currency> getSupportedCurrencies(Markets market) {
    return null;
  }

  @Override
  public String getLag(Markets market) {
    return null;
  }

  @Override
  public Collection<TradesFullLayoutObject> getTrades(Markets market, CurrencyPair currency, long previousTimestamp) {
    return null;
  }

  @Override
  public Ticker getTicker(Markets market, CurrencyPair currencyPair) {
    return null;
  }

  @Override
  public Collection<Markets> getSupportedMarkets() {
    return null;
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols(Markets market) {
    return null;
  }

  @Override
  public String withdrawBTC(double amount, String dest_address) {
    return null;
  }

  @Override
  public String sellBTC(double amount) {
    return null;
  }

  @Override
  public String buyBTC(double amount) {
    return null;
  }
}

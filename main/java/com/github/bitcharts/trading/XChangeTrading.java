package com.github.bitcharts.trading;


import com.github.bitcharts.model.*;
import com.github.bitcharts.trading.interfaces.TradeInterface;
import com.github.bitcharts.trading.util.TradingUtil;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btce.v3.BTCEExchange;
import org.knowm.xchange.currency.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Radu
 * Date: 1/9/14
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class XChangeTrading implements TradeInterface {

  private static final Logger LOG = Logger.getLogger(XChangeTrading.class);

  private Map<Markets, PollingMarketDataService> marketsServiceMap;
  private Map<Markets, Exchange> marketsExchangeMap;

  @PostConstruct
  private void initTradingObject() {
    //create the markets to service map
    marketsServiceMap = new HashMap<>();
    marketsExchangeMap = new HashMap<>();
    try {
      initServices();
    } catch (Exception e) {
      LOG.error(e);
    }
  }

  private void initServices() {
    Exchange btceExchange = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());
    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

   /* Exchange bitcurexExchange = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    Exchange bitstampExchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    Exchange blockchainExchange = ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class.getName());
    Exchange btcchinaExchange = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());
    Exchange bitcoinChartsExchange = ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());
    Exchange campBxExchange = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());
    Exchange anxV3Exchange = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
*/

    marketsExchangeMap.put(Markets.BTCE, btceExchange);
    marketsExchangeMap.put(Markets.KRAKEN, krakenExchange);
        /*marketsExchangeMap.put(Markets.BITCOINCHARTS, bitcoinChartsExchange);
        marketsExchangeMap.put(Markets.BITCUREX, bitcurexExchange);
        marketsExchangeMap.put(Markets.BITSTAMP, bitstampExchange);
        marketsExchangeMap.put(Markets.BTCCHINA, btcchinaExchange);*/

        /*marketsExchangeMap.put(Markets.CAMPBX, campBxExchange);
        //marketsExchangeMap.put(Markets.CAVIRTEX, cavirtexExchange);
        marketsExchangeMap.put(Markets.KRAKEN, krakenExchange);*/

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService btceService = btceExchange.getPollingMarketDataService();
    PollingMarketDataService krakenService = krakenExchange.getPollingMarketDataService();

    /*PollingMarketDataService bitcurexService = bitcurexExchange.getPollingMarketDataService();
    PollingMarketDataService bitstampService = bitstampExchange.getPollingMarketDataService();
    PollingMarketDataService blockchainService = blockchainExchange.getPollingMarketDataService();
    PollingMarketDataService btcchinaService = btcchinaExchange.getPollingMarketDataService();

    PollingMarketDataService campBxService = campBxExchange.getPollingMarketDataService();
    PollingMarketDataService krakenService = krakenExchange.getPollingMarketDataService();
    PollingMarketDataService bitcoinChartsService = bitcoinChartsExchange.getPollingMarketDataService();*/

        /*marketsServiceMap.put(Markets.BITCOINCHARTS, bitcoinChartsService);
        marketsServiceMap.put(Markets.BITCUREX, bitcurexService);
        marketsServiceMap.put(Markets.BITSTAMP, bitstampService);
        marketsServiceMap.put(Markets.BTCCHINA, btcchinaService);*/
    marketsServiceMap.put(Markets.BTCE, btceService);
    marketsServiceMap.put(Markets.KRAKEN, krakenService);
        /*marketsServiceMap.put(Markets.CAMPBX, campBxService);*/
    //marketsServiceMap.put(Markets.CAVIRTEX, cavirtexService);
        /*marketsServiceMap.put(Markets.KRAKEN, krakenService);*/
  }

  @Override
  public Ticker getTicker(String marketName, CurrencyPair currencyPair) {
    try {
      if (EnumUtils.isValidEnum(Markets.class, marketName)) {
        return marketsServiceMap.get(Markets.valueOf(marketName)).getTicker(currencyPair);
      }
    } catch (IOException e) {
      LOG.error(e);
    }

    return new Ticker.Builder().build();
  }

  @Override
  public Set<Markets> getSupportedMarkets() {
    return getMarketsExchangeMap().keySet();
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols(String marketName) {
    Markets market = Markets.valueOf(marketName);
    return getMarketsExchangeMap().get(market).getExchangeSymbols();
  }

  @Override
  public double[] getBalance() {
    return new double[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public <T extends TickerShallowObject> T getLastPrice(String marketName, CurrencyPair cur) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public <T extends TickerShallowObject> T getPrice(Markets market, CurrencyPair currency) {
    // Get the latest ticker data showing BTC to USD
    Ticker ticker;
    TickerShallowObject result = null;

    Date before = new Date();
    LOG.debug("Getting ticker price for market:" + TradingUtil.getMarketIdentifierName(market, currency));
    try {
      ticker = marketsServiceMap.get(market).getTicker(currency);
      result = new TickerFullLayoutObject(currency, ticker.getLast().doubleValue(),
          ticker.getTimestamp(), ticker.getAsk().doubleValue(), -1d,
          ticker.getBid().doubleValue(), ticker.getHigh().doubleValue(), -1d, -1d, -1d,
          ticker.getLow().doubleValue(), ticker.getVolume().doubleValue(), -1d);
    } catch (Throwable e) {
      LOG.error("error while invoking ticker service for:" + TradingUtil.getMarketIdentifierName(market, currency), e);
    }
    LOG.debug("Finished getting ticker price for market:" + TradingUtil.getMarketIdentifierName(market, currency)
        + ", operation took:" + (new Date().getTime() - before.getTime()) + " ms");
    return (T) (result != null ? result : new TickerShallowObject(currency, 0, null));
  }

  @Override
  public Set<org.knowm.xchange.currency.Currency> getSupportedCurrencies(Markets market) {
    Set<Currency> result = new LinkedHashSet<>();
    List<CurrencyPair> supportedCurrencies = marketsExchangeMap.get(market).getExchangeSymbols();
    for (CurrencyPair pair : supportedCurrencies) {
      try {
        org.knowm.xchange.currency.Currency currency = pair.counter;
        if (currency != Currency.BTC) {
          result.add(currency);
        }
      } catch (Throwable e) {
        LOG.warn("Cannot convert unknown currency: " + pair.toString());
      }
    }

    return result;
  }

  @Override
  public String getLag(Markets market) {
    return new String();
  }

  @Override
  public List<TradesFullLayoutObject> getTrades(Markets market, CurrencyPair currency, long previousTimestamp) {
    LOG.info("Getting all the trades since:" + previousTimestamp + " for market:" + TradingUtil.getMarketIdentifierName(market, currency));
    List<TradesFullLayoutObject> result = new ArrayList<>();
    try {
      //the SINCE parameter from the API can be sent here
      Trades trades =  marketsServiceMap.get(market).getTrades(currency, Long.valueOf(previousTimestamp + "000"));   //convert the timestamp to
      // microsecond
      for (Trade trade : trades.getTrades()) {
        //(long tradeId, Date dateDate, double tradePrice, double tradeAmount, Currency currency, Currency tradeItem, TradeType type)
        TradesFullLayoutObject newTrade = new TradesFullLayoutObject(trade.getId(), trade.getTimestamp(),
            trade.getPrice().doubleValue(), trade.getTradableAmount().doubleValue(),
            trade.getCurrencyPair().base,
            trade.getCurrencyPair().counter, TradeType.valueOf(trade.getType().name()));
        result.add(newTrade);
      }
    } catch (Exception e) {
      LOG.error("Could not retrieve trades for market: " + TradingUtil.getMarketIdentifierName(market, currency), e);
    }
    LOG.info("Got " + result.size() + " trades for market:" + TradingUtil.getMarketIdentifierName(market, currency));
    LOG.debug("Trades:" + result.toString());
    return result;
  }

  @Override
  public String withdrawBTC(double amount, String dest_address) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String sellBTC(double amount) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String buyBTC(double amount) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public Map<Markets, PollingMarketDataService> getMarketsServiceMap() {
    return marketsServiceMap;
  }

  public Map<Markets, Exchange> getMarketsExchangeMap() {
    return marketsExchangeMap;
  }
}

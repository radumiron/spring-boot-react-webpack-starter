package com.github.bitcharts.trading;

import java.util.*;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.stereotype.Component;

import com.github.bitcharts.model.*;
import com.github.bitcharts.trading.interfaces.TradeInterface;
import com.github.bitcharts.trading.util.TradingUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Radu
 * Date: 1/9/14
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class XChangeTrading implements TradeInterface {

  private static final Logger LOG = Logger.getLogger(XChangeTrading.class);

  private Map<Markets, MarketDataService> marketsServiceMap;
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
/*    Exchange anxExchange = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
    Exchange bitbayExchange = ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class.getName());
    Exchange bitcoinChartsExchange = ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());
    Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(BitcoindeExchange.class.getName());
    Exchange bitcurexExchange = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    Exchange bitfinexExchange = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());
    Exchange bitkonanExchange = ExchangeFactory.INSTANCE.createExchange(BitKonanExchange.class.getName());
    Exchange bitmarketExchange = ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getName());
    Exchange bitsoExchange = ExchangeFactory.INSTANCE.createExchange(BitsoExchange.class.getName());
    Exchange bitstampExchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    Exchange bittrexExchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    Exchange bitvcExchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    Exchange bleutradeExchange = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getName());
    Exchange blockchainExchange = ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class.getName());
    Exchange btc38Exchange = ExchangeFactory.INSTANCE.createExchange(Btc38Exchange.class.getName());
    Exchange btcchinaExchange = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());
    Exchange btctradeExchange = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    Exchange bterExchange = ExchangeFactory.INSTANCE.createExchange(BTERExchange.class.getName());
    Exchange campbxExchange = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());
    Exchange ccexExchange = ExchangeFactory.INSTANCE.createExchange(CCEXExchange.class.getName());
    Exchange cexioExchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());
    Exchange coinbaseExchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    Exchange coinmateExchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
    Exchange cryptofacilitiesExchange = ExchangeFactory.INSTANCE.createExchange(CryptoFacilitiesExchange.class.getName());
    Exchange cryptonitExchange = ExchangeFactory.INSTANCE.createExchange(CryptonitExchange.class.getName());
    Exchange empoexExchange = ExchangeFactory.INSTANCE.createExchange(EmpoExExchange.class.getName());
    Exchange gatecoinExchange = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName());
    Exchange hitbcExchange = ExchangeFactory.INSTANCE.createExchange(HitbtcExchange.class.getName());
    Exchange huobiExchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    Exchange independentReservecExchange = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class.getName());
    Exchange itbitExchange = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());
    Exchange jubiExchange = ExchangeFactory.INSTANCE.createExchange(JubiExchange.class.getName());



    Exchange lakebtExchange = ExchangeFactory.INSTANCE.createExchange(LakeBTCExchange.class.getName());
    Exchange loyalbitExchange = ExchangeFactory.INSTANCE.createExchange(LoyalbitExchange.class.getName());
    Exchange mercadoExchange = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());
    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(OkCoinExchange.class.getName());
    Exchange poloniexExchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    Exchange quoineExchange = ExchangeFactory.INSTANCE.createExchange(QuoineExchange.class.getName());
    Exchange rippleExchange = ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    Exchange taurusExchange = ExchangeFactory.INSTANCE.createExchange(TaurusExchange.class.getName());
    Exchange therockExchange = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName());
    Exchange vircurexExchange = ExchangeFactory.INSTANCE.createExchange(VircurexExchange.class.getName());*/

      Exchange anxExchange = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
      //Exchange btceExchange = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());
      Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
      Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(OkCoinExchange.class.getName());
      //marketsExchangeMap.put(Markets.BTCE, btceExchange);
      marketsExchangeMap.put(Markets.KRAKEN, krakenExchange);
      marketsExchangeMap.put(Markets.OKCOIN, okcoinExchange);
      marketsExchangeMap.put(Markets.ANXV3, anxExchange);

      // Interested in the public polling market data feed (no authentication)
      MarketDataService anxService = anxExchange.getMarketDataService();
      MarketDataService krakenService = krakenExchange.getMarketDataService();
      MarketDataService okcoinService = okcoinExchange.getMarketDataService();

      marketsServiceMap.put(Markets.KRAKEN, krakenService);
      marketsServiceMap.put(Markets.BTCE, anxService);
      marketsServiceMap.put(Markets.OKCOIN, okcoinService);
  }

  @Override
  public Ticker getTicker(String marketName, CurrencyPair currencyPair) {
    try {
      if (EnumUtils.isValidEnum(Markets.class, marketName)) {
        return marketsServiceMap.get(Markets.valueOf(marketName)).getTicker(currencyPair);
      }
    } catch (Exception e) {
      LOG.error(e);
    }

    return null;
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
  public Collection<Currency> getAllSupportedFiatCurrencies(String cryptoCurrency) {
    //TODO: add proper implementation to retrieve all fiat currencies for a specified crypto currencies
    return Collections.emptyList();
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
        result.add(currency);
      } catch (Exception e) {
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

  public Map<Markets, MarketDataService> getMarketsServiceMap() {
    return marketsServiceMap;
  }

  public Map<Markets, Exchange> getMarketsExchangeMap() {
    return marketsExchangeMap;
  }
}

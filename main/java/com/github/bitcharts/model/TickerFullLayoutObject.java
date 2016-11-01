package com.github.bitcharts.model;

import java.util.Date;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Created with IntelliJ IDEA.
 * User: Radu
 * Date: 6/22/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class TickerFullLayoutObject extends TickerShallowObject implements TickerObject {

   /* *   "result":"success",
            *   "data": {
        *       "high":       **Currency Object - USD**,
        *       "low":        **Currency Object - USD**,
        *       "avg":        **Currency Object - USD**,
        *       "vwap":       **Currency Object - USD**,
        *       "vol":        **Currency Object - BTC**,
        *       "last_local": **Currency Object - USD**,
        *       "last_orig":  **Currency Object - ???**,
        *       "last_all":   **Currency Object - USD**,
        *       "last":       **Currency Object - USD**,
        *       "buy":        **Currency Object - USD**,
        *       "sell":       **Currency Object - USD**,
        *       "now":        "1364689759572564"*/

  private double high;
  private double low;
  private double average;
  private double vwap;
  private double volume;
  private double lastLocal;
  private double lastOrig;
  private double lastAll;
  private double bid;
  private double ask;

  public TickerFullLayoutObject(CurrencyPair currency, double price, Date now, double ask, double average, double bid, double high, double lastAll, double lastLocal, double lastOrig, double low, double volume, double vwap) {
    super(currency, price, now);
    this.ask = ask;
    this.average = average;
    this.bid = bid;
    this.high = high;
    this.lastAll = lastAll;
    this.lastLocal = lastLocal;
    this.lastOrig = lastOrig;
    this.low = low;
    this.volume = volume;
    this.vwap = vwap;
  }

  public TickerFullLayoutObject() {
  }

  public double getAsk() {
    return ask;
  }

  public double getAverage() {
    return average;
  }

  public double getBid() {
    return bid;
  }

  public double getHigh() {
    return high;
  }

  public double getLastAll() {
    return lastAll;
  }

  public double getLastLocal() {
    return lastLocal;
  }

  public double getLastOrig() {
    return lastOrig;
  }

  public double getLow() {
    return low;
  }

  public double getVolume() {
    return volume;
  }

  public double getVwap() {
    return vwap;
  }

  @Override
  public String toString() {
    return "TickerFullLayoutObject{" +
        "ask=" + ask +
        ", high=" + high +
        ", low=" + low +
        ", average=" + average +
        ", vwap=" + vwap +
        ", volume=" + volume +
        ", lastLocal=" + lastLocal +
        ", lastOrig=" + lastOrig +
        ", lastAll=" + lastAll +
        ", bid=" + bid +
        "} " + super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TickerFullLayoutObject)) return false;
    if (!super.equals(o)) return false;

    TickerFullLayoutObject that = (TickerFullLayoutObject) o;

    if (Double.compare(that.high, high) != 0) return false;
    if (Double.compare(that.low, low) != 0) return false;
    if (Double.compare(that.average, average) != 0) return false;
    if (Double.compare(that.vwap, vwap) != 0) return false;
    if (Double.compare(that.volume, volume) != 0) return false;
    if (Double.compare(that.lastLocal, lastLocal) != 0) return false;
    if (Double.compare(that.lastOrig, lastOrig) != 0) return false;
    if (Double.compare(that.lastAll, lastAll) != 0) return false;
    if (Double.compare(that.bid, bid) != 0) return false;
    return Double.compare(that.ask, ask) == 0;

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    long temp;
    temp = Double.doubleToLongBits(high);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(low);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(average);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(vwap);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(volume);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(lastLocal);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(lastOrig);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(lastAll);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(bid);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(ask);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}

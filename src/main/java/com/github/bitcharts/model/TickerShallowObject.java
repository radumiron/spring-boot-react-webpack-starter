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
public class TickerShallowObject implements TickerObject {

  private CurrencyPair currency;
  private double price;
  private Date now;

  public TickerShallowObject(CurrencyPair currency, double price, Date now) {
    this.currency = currency;
    this.price = price;
    this.now = now;
  }

  public TickerShallowObject() {
  }

  public CurrencyPair getCurrency() {
    return currency;
  }

  public double getPrice() {
    return price;
  }

  public Date getNow() {
    return now;
  }

  @Override
  public String toString() {
    return "TickerShallowObject{" +
        "currency=" + currency +
        ", price=" + price +
        ", now=" + now +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TickerShallowObject)) return false;

    TickerShallowObject that = (TickerShallowObject) o;

    if (Double.compare(that.price, price) != 0) return false;
    if (!currency.equals(that.currency)) return false;
    return now.equals(that.now);

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = currency.hashCode();
    temp = Double.doubleToLongBits(price);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + now.hashCode();
    return result;
  }
}

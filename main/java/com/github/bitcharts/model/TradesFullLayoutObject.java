package com.github.bitcharts.model;

import java.util.Date;
import org.knowm.xchange.currency.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: Radu
 * Date: 6/25/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TradesFullLayoutObject extends TradesShallowObject {
  //{"date":1364767201,"price":"92.65","amount":"0.47909825","price_int":"9265000","amount_int":"47909825","tid":"1364767201381791"
  // ,"price_currency":"USD","item":"BTC","trade_type":"bid","primary":"Y","pro
  private Currency currency;
  private Currency tradeItem;
  private TradeType type;
  protected Date date;
  protected Double price;
  protected Double amount;
  protected String tradeId;

  static double PRICE = 0;

  public TradesFullLayoutObject(String tradeId, Date tradeDate, double tradePrice,
                                double tradeAmount, Currency currency,
                                Currency tradeItem,
                                TradeType type) {
    super(tradeDate, tradeAmount, tradePrice);
    this.tradeId = tradeId;
    this.date = tradeDate;
    this.price = tradePrice;
    //this.price = ++PRICE;
    this.amount = tradeAmount;
    this.currency = currency;
    this.tradeItem = tradeItem;
    this.type = type;
  }

  public TradesFullLayoutObject() {
  }

  public Currency getCurrency() {
    return currency;
  }

  public Currency getTradeItem() {
    return tradeItem;
  }

  public TradeType getType() {
    return type;
  }

  public Date getDate() {
    return date;
  }

  public Double getPrice() {
    return price;
  }

  public Double getAmount() {
    return amount;
  }

  public String getTradeId() {
    return tradeId;
  }

  @Override
  public String toString() {
    return "TradesFullLayoutObject{" +
        "tradeId=" + tradeId +
        ", amount=" + getAmount() +
        ", currency=" + currency +
        ", tradeItem=" + tradeItem +
        ", type=" + type +
        ", date=" + getDate() +
        ", price=" + getPrice() +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TradesFullLayoutObject)) return false;

    TradesFullLayoutObject that = (TradesFullLayoutObject) o;

    if (tradeId != that.tradeId) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (currency != null ? currency.hashCode() : 0);
    result = 31 * result + (tradeItem != null ? tradeItem.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (amount != null ? amount.hashCode() : 0);
    result = 31 * result + (tradeId != null ? tradeId.hashCode() : 0);
    return result;
  }
}

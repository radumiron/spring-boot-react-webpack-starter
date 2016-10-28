package com.github.bitcharts.model;

import java.util.Date;
import org.knowm.xchange.currency.Currency;
/**
 * Created with IntelliJ IDEA.
 * User: Radu
 * Date: 6/22/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class TickerShallowObject {

    private Currency currency;
    private double price;
    private Date now;

    public TickerShallowObject(Currency currency, double price, Date now) {
        this.currency = currency;
        this.price = price;
        this.now = now;
    }

    public TickerShallowObject() {
    }

    public Currency getCurrency() {
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
}

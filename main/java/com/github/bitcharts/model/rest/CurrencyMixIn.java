package com.github.bitcharts.model.rest;

import org.knowm.xchange.currency.Currency;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mironr on 11/29/2016.
 */
public abstract class CurrencyMixIn extends Currency {
  CurrencyMixIn(@JsonProperty("code") String code) {
    super(code);
  }

  @JsonProperty("currencyCode")
  public abstract String getCurrencyCode();
}

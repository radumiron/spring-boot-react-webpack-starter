package com.github.bitcharts.model.rest;

import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.currency.Currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mironr on 11/29/2016.
 */
public abstract class CurrencyMixIn extends Currency {
  CurrencyMixIn(@JsonProperty("code") String code) {
    super(code);
  }

  @JsonProperty("currencyCode")
  private String code;

  @JsonIgnore
  public abstract Currency getCodeCurrency(String code);

  @JsonIgnore
  public abstract Currency getIso4217Currency();

  @JsonIgnore
  public abstract Currency getCommonlyUsedCurrency();
}

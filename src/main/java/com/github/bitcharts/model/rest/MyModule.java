package com.github.bitcharts.model.rest;

import org.knowm.xchange.currency.Currency;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by mironr on 11/29/2016.
 */
public class MyModule extends SimpleModule {
  public MyModule() {
    super("ModuleName", new Version(0, 0, 1, null));
  }

  @Override
  public void setupModule(SetupContext context) {
    context.setMixInAnnotations(Currency.class, CurrencyMixIn.class);
  }
}

package com.github.bitcharts.model.rest;

import com.github.bitcharts.model.Markets;

import lombok.Data;

/**
 * Created by mironr on 11/10/2016.
 */
@Data
public class MarketsJSON {
  private String name;

  public MarketsJSON(Markets name) {
    this.name = name.name();
  }
}

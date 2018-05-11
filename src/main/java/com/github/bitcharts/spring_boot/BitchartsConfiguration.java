package com.github.bitcharts.spring_boot; /**
 * Created by mironr on 10/27/2016.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bitcharts.model.rest.CurrencyMixIn;
import com.github.bitcharts.settings.Global;
import org.knowm.xchange.currency.Currency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BitchartsConfiguration {

  public static void main(String[] args) {
    System.setProperty("http.proxyHost", Global.PROXY_SERVER_HOST);
    System.setProperty("http.proxyPort", Global.PROXY_SERVER_PORT);

    SpringApplication.run(BitchartsConfiguration.class, args);
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(Currency.class, CurrencyMixIn.class);
    return mapper;
  }

}

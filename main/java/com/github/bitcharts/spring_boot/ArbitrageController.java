package com.github.bitcharts.spring_boot;

import java.util.Collection;
import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.bitcharts.model.Markets;

/**
 * Created by Radu on 5/24/2017.
 */
@Controller
@RequestMapping("/arbitrage")
@ComponentScan("com.github.bitcharts.trading")
public class ArbitrageController {

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody
  Map<String, Collection<Markets>> supportedMarkets() {
    return null;
  }
}

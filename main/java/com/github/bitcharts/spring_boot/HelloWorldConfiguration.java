package com.github.bitcharts.spring_boot; /**
 * Created by mironr on 10/27/2016.
 */
import com.github.bitcharts.settings.Global;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldConfiguration {

    public static void main(String[] args) {
        System.setProperty("http.proxyHost", Global.PROXY_SERVER_HOST);
        System.setProperty("http.proxyPort", Global.PROXY_SERVER_PORT);
        SpringApplication.run(HelloWorldConfiguration.class, args);
    }

}

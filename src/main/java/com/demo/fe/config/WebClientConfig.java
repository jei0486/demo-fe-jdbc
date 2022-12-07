package com.demo.fe.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${custom.api.url.demo-api}")
    String demoApiUrl;


    @Bean
    public WebClient getWebClient() {

       HttpClient httpClient = HttpClient.create()
               .tcpConfiguration(client ->
                       client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)
                               .doOnConnected(conn -> conn
                                       .addHandlerLast(new ReadTimeoutHandler(10))
                                       .addHandlerLast(new WriteTimeoutHandler(10))));
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
                .baseUrl(demoApiUrl)
                .clientConnector(connector)
                .build();
    }
}

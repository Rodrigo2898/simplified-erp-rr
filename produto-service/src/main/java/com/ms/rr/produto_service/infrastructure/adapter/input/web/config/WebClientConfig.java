package com.ms.rr.produto_service.infrastructure.adapter.input.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${integracao.pessoas-service.url}")
    private String pessoaHost;

    public WebClientConfig(String pessoaHost) {
        this.pessoaHost = pessoaHost;
    }

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(pessoaHost)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}

package com.ms.rr.produto_service.infrastructure.adapter.input.web.client;

import com.ms.rr.produto_service.application.dto.in.FornecedorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class PessoaServiceClient {

    private final WebClient webClient;

    public PessoaServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<FornecedorDTO> buscaFornecedorPoId(Long id) {
        return webClient.get()
                .uri("/api/fornecedor/".concat(id.toString()))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Fornecedor não encntrado, informar um id válido")))
                .bodyToMono(FornecedorDTO.class)
                .timeout(Duration.ofSeconds(5))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)));
    }
}

package com.ms.rr.produto_service.adapter.output.client;

import com.ms.rr.produto_service.domain.dto.out.FornecedorResponse;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.model.FornecedorDomain;
import com.ms.rr.produto_service.domain.port.output.FornecedorOutputPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.naming.ServiceUnavailableException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;

@Component
public class PessoaWebClientAdapter implements FornecedorOutputPort {

    private final WebClient webClient;

    public PessoaWebClientAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<FornecedorDomain> findFornecedorById(Long id) {
        return webClient.get()
                .uri("/api/fornecedor/".concat(id.toString()))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new FornecedorNotFoundException(
                                FORNECEDOR_NOT_FOUND.params(id.toString()).getMessage())))
                .bodyToMono(FornecedorResponse.class)
                .map(this::toDomain)
                .timeout(Duration.ofSeconds(5))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100))
                .filter(throwable -> throwable instanceof ServiceUnavailableException
                        || throwable instanceof TimeoutException));
    }

    private FornecedorDomain toDomain(FornecedorResponse fornecedor) {
        return new FornecedorDomain(
                fornecedor.id(),
                fornecedor.nome(),
                fornecedor.email(),
                fornecedor.telefone(),
                fornecedor.cpnj(),
                fornecedor.razaoSocial()
        );
    }
}

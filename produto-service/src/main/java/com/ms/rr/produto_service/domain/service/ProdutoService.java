package com.ms.rr.produto_service.domain.service;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.exception.InvalidPaginationException;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.*;

@Service
public class ProdutoService implements ProdutoUseCase {

    private final ProdutoOutputPort produtoOutputPort;
    private final FornecedorOutputPort fornecedorOutputPort;

    public ProdutoService(ProdutoOutputPort produtoOutputPort, FornecedorOutputPort fornecedorOutputPort) {
        this.produtoOutputPort = produtoOutputPort;
        this.fornecedorOutputPort = fornecedorOutputPort;
    }

    @Override
    public Mono<Void> salvar(CreateProduto createProduto) {
        return fornecedorOutputPort.findFornecedorById(createProduto.fornecedorId())
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new FornecedorNotFoundException(FORNECEDOR_NOT_FOUND.params(createProduto.fornecedorId().toString())
                                .getMessage())
                )))
                .flatMap(fornecedorDTO -> produtoOutputPort.save(createProduto.toDomain()))
                .onErrorMap(e -> {
                    if (!(e instanceof FornecedorNotFoundException)) {
                        return new RuntimeException("Erro ao salvar produto", e);
                    }
                    return e;
                });
    }

    @Override
    public ProdutoResponse buscarPorId(Long id) {
        var produto = ProdutoResponse.fromDomain(produtoOutputPort.findById(id));
        if (produto == null) {
            throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
        }
        return produto;
    }

    @Override
    public Page<ProdutoResponse> buscarTodosProdutos(int page, int size) {
        if (page < 0) {
            throw new InvalidPaginationException(INVALID_PAGE_NUMBER.getMessage());
        }
        if (size <= 0) {
            throw new InvalidPaginationException(INVALID_PAGE_SIZE.getMessage());
        }
        Pageable pageable = PageRequest.of(page, size);
        return produtoOutputPort.findAll(pageable)
                .map(ProdutoResponse::fromDomain);
    }

    @Override
    public Page<ProdutoResponse> buscarProdutosPorCategoria(String categoria, int page, int size) {
        if (page < 0) {
            throw new InvalidPaginationException(INVALID_PAGE_NUMBER.getMessage());
        }
        if (size <= 0) {
            throw new InvalidPaginationException(INVALID_PAGE_SIZE.getMessage());
        }
        Pageable pageable = PageRequest.of(page, size);
        return produtoOutputPort.findAllByCategoria(categoria, pageable)
                .map(ProdutoResponse::fromDomain);
    }

    /**
     * Todo
     * Otimizar metodo
     * Melhorar a atualização de produtos com os fornecedores vinculados
     * */
    @Override
    public ProdutoResponse atualizar(Long id, UpdateProduto updateProduto) {
        var produtoExistente = ProdutoResponse.fromDomain(produtoOutputPort.findById(id));
        if (produtoExistente == null) {
            throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
        }
        produtoOutputPort.save(updateProduto.toDomain(produtoExistente.id()))
                .onErrorMap(e -> new RuntimeException("Erro ao atualizar produto", e));
        return ProdutoResponse.fromDomain(produtoOutputPort.findById(id));
    }

    @Override
    public void excluir(Long id) {
        if (produtoOutputPort.findById(id) != null) {
            produtoOutputPort.delete(id);
            return;
        }
        throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
    }
}

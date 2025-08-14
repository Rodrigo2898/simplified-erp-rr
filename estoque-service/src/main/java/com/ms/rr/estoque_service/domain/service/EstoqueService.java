package com.ms.rr.estoque_service.domain.service;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService implements EstoqueUseCase {

    private final EstoqueOutputPort estoqueOutputPort;

    public EstoqueService(EstoqueOutputPort estoqueOutputPort) {
        this.estoqueOutputPort = estoqueOutputPort;
    }

    @Override
    public void salvar(CreateEstoque estoque) {
        estoqueOutputPort.findByNomeProduto(estoque.nomeProduto())
                .ifPresentOrElse(produtoExistente -> {
                    EstoqueDomain updateProduto = produtoExistente
                            .addQuantidadeAndUpdateDataAtualizacao(estoque.quantidade());
                    estoqueOutputPort.save(updateProduto);
                }, () -> {
                    EstoqueDomain newProduto = estoque.toDomain();
                    estoqueOutputPort.save(newProduto);
                });
    }
}

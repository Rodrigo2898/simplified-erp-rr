package com.ms.rr.estoque_service.domain.service;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class EstoqueService implements EstoqueUseCase {

    private final EstoqueOutputPort estoqueOutputPort;
    private final MongoTemplate mongoTemplate;

    public EstoqueService(EstoqueOutputPort estoqueOutputPort, MongoTemplate mongoTemplate) {
        this.estoqueOutputPort = estoqueOutputPort;
        this.mongoTemplate = mongoTemplate;
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

    @Override
    public Page<EstoqueResponse> buscandoPorTipoProduto(String tipoProduto, PageRequest pageRequest) {
        return estoqueOutputPort.findAllByTipo(tipoProduto, pageRequest)
                .map(EstoqueResponse::fromDomain);
    }

    @Override
    public BigDecimal buscaTotalPorTipoProduto(String tipoProduto) {
        var aggregations = newAggregation(
                match(Criteria.where("tipoProduto").is(tipoProduto)),
                group().sum("quantidade").as("quantidade")
        );

        var response = mongoTemplate.aggregate(aggregations, "estoque", Document.class);
        return new BigDecimal(Objects.requireNonNull(response.getUniqueMappedResult()).get("quantidade").toString());
    }
}

package com.ms.rr.estoque_service.domain.service;

import com.ms.rr.estoque_service.adapter.output.persistence.document.Estoque;
import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundInEstoqueException;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import com.ms.rr.estoque_service.domain.utils.DateFormatterUtil;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.*;
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
    public EstoqueResponse buscarPorNome(String nome) {
        return estoqueOutputPort.findByNomeProduto(nome)
                .map(EstoqueResponse::fromDomain)
                .orElseThrow(() -> new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(nome).getMessage()));
    }

    @Override
    public Page<EstoqueResponse> buscarTodos(PageRequest pageRequest) {
        return estoqueOutputPort.findAll(pageRequest)
                .map(EstoqueResponse::fromDomain);
    }

    @Override
    public Page<EstoqueResponse> buscarPorTipoProduto(String tipoProduto, PageRequest pageRequest) {
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

    @Override
    public void decrementaPorNome(String nome, Integer quantidade) {
        var produtoExistente = estoqueOutputPort.findByNomeProduto(nome);
        if (produtoExistente.isEmpty()) {
            throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(nome).getMessage());
        }
        if (produtoExistente.get().quantidade() <= 0 || produtoExistente.get().quantidade() < quantidade) {
            throw new ProdutoNotFoundInEstoqueException(PRODUTO_NOT_FOUND_IN_ESTOQUE.params(nome).getMessage());
        }
        Query query = new Query(Criteria.where("nomeProduto").is(nome));
        Update update = new Update()
                .inc("quantidade",  -quantidade)
                .set("dataAtualizacao", LocalDateTime.now().format(DateFormatterUtil.customFormatter()));
        mongoTemplate.updateFirst(query, update, Estoque.class);
    }

    @Override
    public void deletaPorId(String id) {
        estoqueOutputPort.findById(id)
                .ifPresentOrElse(estoqueOutputPort::delete, () -> {
                    throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(id).getMessage());
                });
    }
}

package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.domain.service.impl.FornecedorServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorApi implements BaseApi<CreateFornecedor, FornecedorResponse, Long> {

    private final FornecedorServiceImpl fornecedorService;

    public FornecedorApi(FornecedorServiceImpl fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @Override
    public void create(CreateFornecedor dto) {
        fornecedorService.salvar(dto.toDomain());
    }

    @Override
    public List<FornecedorResponse> list() {
        return fornecedorService.buscarTodos()
                .stream()
                .map(FornecedorResponse::fromDomain)
                .toList();
    }

    @Override
    public FornecedorResponse findById(Long id) {
        return FornecedorResponse.fromDomain(fornecedorService.buscarPorId(id));
    }

    @Override
    public void delete(Long id) {
        fornecedorService.excluir(id);
    }
}

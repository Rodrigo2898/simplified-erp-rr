package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.application.port.input.FornecedorUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorApi implements BaseApi<CreateFornecedor, FornecedorResponse, Long> {

    private final FornecedorUseCase fornecedorUseCase;

    public FornecedorApi(FornecedorUseCase fornecedorUseCase) {
        this.fornecedorUseCase = fornecedorUseCase;
    }

    @Override
    public void create(CreateFornecedor dto) {
        fornecedorUseCase.salvar(dto.toDomain());
    }

    @Override
    public List<FornecedorResponse> list() {
        return fornecedorUseCase.buscarTodos()
                .stream()
                .map(FornecedorResponse::fromDomain)
                .toList();
    }

    @Override
    public FornecedorResponse findById(Long id) {
        return FornecedorResponse.fromDomain(fornecedorUseCase.buscarPorId(id));
    }

    @Override
    public void delete(Long id) {
        fornecedorUseCase.excluir(id);
    }
}

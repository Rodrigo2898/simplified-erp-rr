package com.ms.rr.pessoa_service.domain.service;

import com.ms.rr.pessoa_service.domain.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.domain.port.input.FornecedorUseCase;
import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.domain.port.output.PessoaCriadaOutpuPort;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.model.PessoaCriadaEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorServiceImpl implements FornecedorUseCase {

    private final FornecedorOutputPort fornecedorOutputPort;
    private final PessoaCriadaOutpuPort pessoaCriadaOutpuPort;

    public FornecedorServiceImpl(FornecedorOutputPort fornecedorOutputPort, PessoaCriadaOutpuPort pessoaCriadaOutpuPort) {
        this.fornecedorOutputPort = fornecedorOutputPort;
        this.pessoaCriadaOutpuPort = pessoaCriadaOutpuPort;
    }

    @Transactional
    @Override
    public void salvar(CreateFornecedor createFornecedor) {
        fornecedorOutputPort.save(createFornecedor.toDomain());
        pessoaCriadaOutpuPort.sendMessage(eventFromFornecedorDomain(createFornecedor.toDomain()));
    }

    @Override
    public FornecedorResponse buscarPorId(Long id) {
        return fornecedorOutputPort.findById(id)
                .map(FornecedorResponse::fromDomain)
                .orElseThrow();
    }

    @Override
    public List<FornecedorResponse> buscarTodos() {
        return fornecedorOutputPort.findAll()
                .stream().map(FornecedorResponse::fromDomain)
                .toList();
    }

    @Transactional
    @Override
    public void atualizar(Long id, UpdateFornecedor updateFornecedor) {
        fornecedorOutputPort.update(id, updateFornecedor.toDomain(id));
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        fornecedorOutputPort.deleteById(id);
    }

    private PessoaCriadaEvent eventFromFornecedorDomain(FornecedorDomain pessoa) {
        return new PessoaCriadaEvent(
                UUID.randomUUID(),
                pessoa.id(),
                pessoa.nome(),
                pessoa.email(),
                pessoa.telefone()
        );
    }

    @Override
    public FornecedorResponse buscarPorCnpj(String cnpj) {
        return FornecedorResponse
                .fromDomain(fornecedorOutputPort.findFornecedorByCnpj(cnpj));
    }
}

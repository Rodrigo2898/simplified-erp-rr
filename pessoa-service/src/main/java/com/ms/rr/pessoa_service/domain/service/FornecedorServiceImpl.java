package com.ms.rr.pessoa_service.domain.service;

import com.ms.rr.pessoa_service.domain.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.domain.exception.BaseErrorMessage;
import com.ms.rr.pessoa_service.domain.exception.FornecedorException;
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
        if (fornecedorOutputPort.emailExists(createFornecedor.email())) {
            throw new FornecedorException(BaseErrorMessage.EXISTS_PESSOA_EMAIL.getMessage());
        }

        if (fornecedorOutputPort.cnpjExists(createFornecedor.cnpj())) {
            throw new FornecedorException(BaseErrorMessage.EXISTS_PESSOA_FORNECEDOR_CNPJ.getMessage());
        }

        fornecedorOutputPort.save(createFornecedor.toDomain());
        pessoaCriadaOutpuPort.sendMessage(eventFromFornecedorDomain(createFornecedor.toDomain()));
    }

    @Override
    public FornecedorResponse buscarPorId(Long id) {
        return fornecedorOutputPort.findById(id)
                .map(FornecedorResponse::fromDomain)
                .orElseThrow(() -> new FornecedorException(BaseErrorMessage.FORNECEDOR_NOT_FOUND.getMessage()));
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
        if (fornecedorOutputPort.findById(id).isEmpty()) {
            throw new FornecedorException(BaseErrorMessage.FORNECEDOR_NOT_FOUND.getMessage());
        }
        fornecedorOutputPort.update(id, updateFornecedor.toDomain(id));
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        if (fornecedorOutputPort.findById(id).isEmpty()) {
            throw new FornecedorException(BaseErrorMessage.FORNECEDOR_NOT_FOUND.getMessage());
        }
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
        FornecedorDomain fornecedor = fornecedorOutputPort.findFornecedorByCnpj(cnpj);
        if (fornecedor == null) {
            throw new FornecedorException(BaseErrorMessage.FORNECEDOR_NOT_FOUND.getMessage());
        }
        return FornecedorResponse
                .fromDomain(fornecedor);
    }
}

package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.port.input.FornecedorUseCase;
import com.ms.rr.pessoa_service.application.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.application.port.output.PessoaCriadaOutpuPort;
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

    @Override
    @Transactional
    public void salvar(FornecedorDomain pessoa) {
        fornecedorOutputPort.save(pessoa);
        pessoaCriadaOutpuPort.sendMessage(eventFromFornecedorDomain(pessoa));
    }

    @Override
    public FornecedorDomain buscarPorId(Long id) {
        return fornecedorOutputPort.findById(id).orElseThrow();
    }

    @Override
    public List<FornecedorDomain> buscarTodos() {
        return fornecedorOutputPort.findAll();
    }

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
}

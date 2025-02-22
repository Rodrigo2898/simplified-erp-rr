package com.ms.rr.pessoa_service.domain.service;

import com.ms.rr.pessoa_service.domain.model.Cliente;
import com.ms.rr.pessoa_service.domain.model.Endereco;
import com.ms.rr.pessoa_service.domain.model.Fornecedor;
import com.ms.rr.pessoa_service.domain.repository.ClienteRepository;
import com.ms.rr.pessoa_service.domain.repository.EnderecoRepository;
import com.ms.rr.pessoa_service.domain.repository.FornecedorRepository;

import java.util.List;

public class PessoaService {

    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(ClienteRepository clienteRepository, FornecedorRepository fornecedorRepository,
                         EnderecoRepository enderecoRepository) {
        this.clienteRepository = clienteRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public void cadastrarCliente(Cliente cliente) {
        this.clienteRepository.save(cliente);
    }

    public void cadastrarFornecedor(Fornecedor fornecedor) {
        this.fornecedorRepository.save(fornecedor);
    }

    List<Endereco> buscarEnderecoPorPessoaId(Long pessoaId) {
        return this.enderecoRepository.findByPessoaId(pessoaId);
    }
}

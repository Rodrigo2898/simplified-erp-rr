package com.ms.rr.pessoa_service.application.port.input;

import java.util.List;
import java.util.Optional;

public interface PessoaUseCase<CreateT, TResponse, ID> {
    void salvar(CreateT pessoa);
    Optional<TResponse> buscarPorId(ID id);
    List<TResponse> buscarTodos();
    void excluir(ID id);
}

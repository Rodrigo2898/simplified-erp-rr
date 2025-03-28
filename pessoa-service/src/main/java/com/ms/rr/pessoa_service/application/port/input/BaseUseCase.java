package com.ms.rr.pessoa_service.application.port.input;

import java.util.List;
import java.util.Optional;

public interface BaseUseCase<Domain, ID> {
    void salvar(Domain domain);
    Optional<Domain> buscarPorId(ID id);
    List<Domain> buscarTodos();
    void excluir(ID id);
}

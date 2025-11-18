package com.ms.rr.pessoa_service.domain.port.input;

import java.util.List;

public interface BaseUseCase<Create, Update, Response, ID> {

    void salvar(Create domain);

    Response buscarPorId(ID id);

    List<Response> buscarTodos();

    Response atualizar(ID id, Update domain);

    void excluir(ID id);
}

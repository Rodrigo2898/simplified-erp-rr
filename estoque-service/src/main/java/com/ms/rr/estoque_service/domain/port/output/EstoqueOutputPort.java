package com.ms.rr.estoque_service.domain.port.output;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;

public interface EstoqueOutputPort {

    void save(EstoqueDomain estoqueDomain);
}

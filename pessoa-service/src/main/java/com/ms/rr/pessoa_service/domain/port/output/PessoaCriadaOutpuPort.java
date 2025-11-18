package com.ms.rr.pessoa_service.domain.port.output;

import com.ms.rr.pessoa_service.domain.model.PessoaCriadaEvent;

public interface PessoaCriadaOutpuPort {

    void sendMessage(PessoaCriadaEvent event);
}

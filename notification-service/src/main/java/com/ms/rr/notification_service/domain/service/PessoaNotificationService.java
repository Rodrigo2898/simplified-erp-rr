package com.ms.rr.notification_service.domain.service;

import com.ms.rr.notification_service.domain.model.PessoaCriadaEvent;
import com.ms.rr.notification_service.domain.port.input.NotificationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PessoaNotificationService implements NotificationUseCase<PessoaCriadaEvent> {

    private static final Logger log = LoggerFactory.getLogger(PessoaNotificationService.class);

    @Override
    public void sendEmail(PessoaCriadaEvent pessoaCriadaEvent) {
        log.info("Enviando e-mail para: {}", pessoaCriadaEvent.getEmail());
    }

    @Override
    public void sendSMS(PessoaCriadaEvent pessoaCriadaEvent) {
        log.info("Enviando sms para: {}", pessoaCriadaEvent.getTelefone());
    }
}

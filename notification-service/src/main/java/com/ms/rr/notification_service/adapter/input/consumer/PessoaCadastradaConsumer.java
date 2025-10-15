package com.ms.rr.notification_service.adapter.input.consumer;

import com.ms.rr.notification_service.domain.model.PessoaCriadaEvent;
import com.ms.rr.notification_service.domain.port.input.NotificationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PessoaCadastradaConsumer {

    private static final Logger log = LoggerFactory.getLogger(PessoaCadastradaConsumer.class);
    private final NotificationUseCase<PessoaCriadaEvent> pessoaNotificationUseCase;

    public PessoaCadastradaConsumer(NotificationUseCase<PessoaCriadaEvent> pessoaNotificationUseCase) {
        this.pessoaNotificationUseCase = pessoaNotificationUseCase;
    }

    @KafkaListener(
            topics = "${topicos.pessoa-cadastrada.cosumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(PessoaCriadaEvent pessoaCriadaEvent) {
        log.info("Pessoa Cadastrada: {}", pessoaCriadaEvent.getNome());
        sendEmail(pessoaCriadaEvent);
        sendSMS(pessoaCriadaEvent);
    }

    @Async("threadPoolTaskExecutor")
    public void sendEmail(PessoaCriadaEvent pessoaCriadaEvent) {
        try {
            pessoaNotificationUseCase.sendEmail(pessoaCriadaEvent);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para pessoa: {}", pessoaCriadaEvent.getNome(), e);
        }
    }

    @Async("threadPoolTaskExecutor")
    public void sendSMS(PessoaCriadaEvent pessoaCriadaEvent) {
        try {
            pessoaNotificationUseCase.sendSMS(pessoaCriadaEvent);
        } catch (Exception e) {
            log.error("Erro ao enviar SMS para pessoa: {}", pessoaCriadaEvent.getNome(), e);
        }
    }
}

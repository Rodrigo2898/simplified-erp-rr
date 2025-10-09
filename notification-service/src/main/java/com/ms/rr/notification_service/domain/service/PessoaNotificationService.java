package com.ms.rr.notification_service.domain.service;

import com.ms.rr.notification_service.domain.model.PessoaCriadaEvent;
import com.ms.rr.notification_service.domain.port.input.NotificationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class PessoaNotificationService implements NotificationUseCase<PessoaCriadaEvent> {

    private static final Logger log = LoggerFactory.getLogger(PessoaNotificationService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public PessoaNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(PessoaCriadaEvent pessoaCriadaEvent) {
        log.info("Enviando e-mail para: {}", pessoaCriadaEvent.getEmail());
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(pessoaCriadaEvent.getEmail());
            messageHelper.setSubject(String.format("%s seu cadastro foi efetuado com sucesso", pessoaCriadaEvent.getNome()));
            messageHelper.setText(String.format("""
                            Olá, %s

                            Seu email: %s foi verificado com sucesso.

                            Atenciosamente,
                            ERP-SHOP
                            """,
                    pessoaCriadaEvent.getNome(),
                    pessoaCriadaEvent.getEmail()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Email enviado com sucesso para: {}", pessoaCriadaEvent.getEmail());
        } catch (MailException e) {
            log.error("Problema no envio do email: {}", pessoaCriadaEvent.getEmail());
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    @Override
    public void sendSMS(PessoaCriadaEvent pessoaCriadaEvent) {
        log.info("Enviando sms para: {}", pessoaCriadaEvent.getTelefone());
    }
}

package com.ms.rr.notification_service.domain.port.input;

public interface NotificationUseCase<Event> {

    void sendEmail(Event event);
}

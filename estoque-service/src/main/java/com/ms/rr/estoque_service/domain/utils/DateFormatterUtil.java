package com.ms.rr.estoque_service.domain.utils;

import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {

    public static DateTimeFormatter customFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }
}

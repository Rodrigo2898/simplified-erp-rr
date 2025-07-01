package com.ms.rr.produto_service.domain.exception;

import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class BaseErrorMessage {

    private final String DEFAULT_RESOURCE = "messages";

    public static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic");
    public static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.notFound");
    public static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOW = new BaseErrorMessage("generic.methodNotAllow");
    public static final BaseErrorMessage GENERIC_BAD_REQUEST = new BaseErrorMessage("generic.badRequest");
    public static final BaseErrorMessage GENERIC_MAX_RETRIES = new BaseErrorMessage("generic.maxRetries");
    public static final BaseErrorMessage PRODUTO_NOT_FOUND = new BaseErrorMessage("NotFound.produto");
    public static final BaseErrorMessage FORNECEDOR_NOT_FOUND = new BaseErrorMessage("NotFound.produto.fornecedor");

    private final String key;
    private String[] params;

    public BaseErrorMessage(String key) {
        this.key = key;
    }

    public BaseErrorMessage params(String... params) {
        this.params = params;
        return this;
    }

    public String getMessage() {
        var message = tryGetMessageFromBundle();
        if (ArrayUtils.isNotEmpty(params)) {
            final var fmt = new MessageFormat(message);
            message = fmt.format(params);
        }
        return message;
    }

    private String tryGetMessageFromBundle() {
        return getResourceBundle().getString(key);
    }

    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(DEFAULT_RESOURCE);
    }
}

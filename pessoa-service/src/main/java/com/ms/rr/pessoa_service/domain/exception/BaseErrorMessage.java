package com.ms.rr.pessoa_service.domain.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class BaseErrorMessage {

    private final String DEFAULT_RESOURCE = "messages";

    public static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic");
    public static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.notFound");
    public static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOW = new BaseErrorMessage("generic.methodNotAllow");
    public static final BaseErrorMessage NOTBLANK_PESSOA_EMAIL = new BaseErrorMessage("NotBlank.userRequest.pessoa.email");
    public static final BaseErrorMessage NOTBLANK_PESSOA_NOME = new BaseErrorMessage("NotBlank.userRequest.pessoa.nome");
    public static final BaseErrorMessage VERIFY_PESSOA_EMAIL = new BaseErrorMessage("Verify.userRequest.pessoa.email");
    public static final BaseErrorMessage EXISTS_PESSOA_EMAIL = new BaseErrorMessage("Exists.userRequest.email");
    public static final BaseErrorMessage EXISTS_PESSOA_TELEFONE= new BaseErrorMessage("Exists.userRequest.telefone");
    public static final BaseErrorMessage EXISTS_PESSOA_FORNECEDOR_CNPJ = new BaseErrorMessage("Exists.userRequest.fornecedor.cnpf");
    public static final BaseErrorMessage EXISTS_PESSOA_CLIENTE_CPF = new BaseErrorMessage("Exists.userRequest.cliente.cpf");
    public static final BaseErrorMessage FORNECEDOR_NOT_FOUND = new BaseErrorMessage("NotFound.pessoa.fornecedor");
    public static final BaseErrorMessage CLIENTE_NOT_FOUND = new BaseErrorMessage("NotFound.pessoa.cliente");

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
        if (isEmpty(params)) {
            final var fmt = new MessageFormat(message);
            message = fmt.format(params);
        }
        return message;
    }

    private boolean isEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }

    private String tryGetMessageFromBundle() {
        return getResourceBundle().getString(key);
    }

    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(DEFAULT_RESOURCE);
    }
}

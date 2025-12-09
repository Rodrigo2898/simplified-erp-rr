-- Adicionando colunas referente ao endereço

ALTER TABLE tb_pessoa
    ADD cep VARCHAR(255) NOT NULL DEFAULT '',
    ADD nome_rua VARCHAR(100) NOT NULL DEFAULT '',
    ADD numero_rua INTEGER NOT NULL DEFAULT 0,
    ADD bairro VARCHAR(100) NOT NULL DEFAULT '',
    ADD cidade VARCHAR(100) NOT NULL DEFAULT '',
    ADD estado VARCHAR(100) NOT NULL DEFAULT '';

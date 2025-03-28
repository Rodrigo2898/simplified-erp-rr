-- Criação do tipo ENUM
CREATE TYPE pessoa_type_enum AS ENUM ('CLIENTE', 'FORNECEDOR');

CREATE TABLE tb_pessoa (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(250) NOT NULL,
    tipo_pessoa pessoa_type_enum NOT NULL,
    cpf VARCHAR(14) UNIQUE,  -- Apenas para CLIENTE
    data_cadastro DATE,      -- Apenas para CLIENTE
    cnpj VARCHAR(18) UNIQUE, -- Apenas para FORNECEDOR
    razao_social VARCHAR(255),-- Apenas para FORNECEDOR
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
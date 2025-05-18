
CREATE TABLE tb_produto (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100),
    preco NUMERIC(19, 2),
    fornecedor_id BIGINT
);
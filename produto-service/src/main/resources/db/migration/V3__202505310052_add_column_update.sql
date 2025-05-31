-- Migration 3 (corrigindo o problema)
ALTER TABLE tb_produto ADD COLUMN updated_at TIMESTAMP;

-- Criando função para atualizar o campo updated_at
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criando o Trigger
CREATE TRIGGER trg_update_updated_at
    BEFORE UPDATE ON tb_pessoa
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();



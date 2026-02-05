DO
$$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'pessoa_service') THEN
      CREATE DATABASE pessoa_service;
END IF;
END
$$;

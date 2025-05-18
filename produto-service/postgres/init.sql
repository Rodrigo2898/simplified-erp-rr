DO
$$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'produto_service') THEN
      CREATE DATABASE produto_service;
END IF;
END
$$;

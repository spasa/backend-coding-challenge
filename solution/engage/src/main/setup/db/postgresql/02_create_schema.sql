DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA public AUTHORIZATION engage;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public IS 'standard public schema';

CREATE ROLE paf2023_role;
GRANT CONNECT ON DATABASE paf2023 TO paf2023_role;
GRANT USAGE ON SCHEMA public TO paf2023_role;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO paf2023_role;

-- CREATE USER paf2023 WITH PASSWORD 'paf2023';
GRANT paf2023_role TO paf2023;
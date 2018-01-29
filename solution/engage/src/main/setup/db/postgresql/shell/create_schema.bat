@ECHO on

set engageopts=-e -h localhost -d engage -U engage -f

psql.exe %engageopts% ..\02_create_schema.sql
psql.exe %engageopts% ..\03_create_sequences.sql
psql.exe %engageopts% ..\04_create_tables.sql
psql.exe %engageopts% ..\04_1_partial_indexes.sql
psql.exe %engageopts% ..\05_triggers.sql
for /r ..\functions\ %%g in (*.sql) do psql.exe %engageopts% %%g
psql.exe %engageopts% ..\06_default_data.sql
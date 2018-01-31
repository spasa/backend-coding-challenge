@ECHO on

path C:\Program Files\PostgreSQL\9.6\bin;.;
set opts=-e -h localhost -U postgres

echo Recreating Engage DB Schema...

psql.exe %opts% -c "drop database engage"
psql.exe %opts% -c "create user engage with encrypted password 'engage' valid until '2020-12-31'"
psql.exe %opts% -c "create database engage with owner=engage"
psql.exe %opts% -d engage -c "DROP SCHEMA IF EXISTS public CASCADE;"

create_schema.bat
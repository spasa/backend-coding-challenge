#!/bin/bash
echo "Create schema script"

CSDIR=$(cd $(dirname "$0"); pwd)
echo "Dir: "$CSDIR

psql -h localhost engage -U postgres -c "DROP SCHEMA IF EXISTS public CASCADE;"

psql -h localhost engage -U engage < "$CSDIR/../02_create_schema.sql"
psql -h localhost engage -U engage < "$CSDIR/../03_create_sequences.sql"
psql -h localhost engage -U engage < "$CSDIR/../04_create_tables.sql"
psql -h localhost engage -U engage < "$CSDIR/../04_1_partial_indexes.sql"
psql -h localhost engage -U engage < "$CSDIR/../05_triggers.sql"

for file in $CSDIR/../functions/*.sql; 
do
    psql -h localhost engage -U engage < "$file"
done

psql -h localhost engage -U engage < "$CSDIR/../06_default_data.sql"
#!/bin/bash
echo "Create database script"

psql -h localhost -U postgres -c "drop database engage"
psql -h localhost -U postgres -c "create database engage with owner=engage;"


CDDIR=$(cd $(dirname "$0"); pwd)
echo "Dir: "$CDDIR

source "$CDDIR/create_schema.sh"